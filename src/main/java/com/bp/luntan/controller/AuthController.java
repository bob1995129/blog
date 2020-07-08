package com.bp.luntan.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;

import com.bp.luntan.common.lang.Result;
import com.bp.luntan.entity.User;
import com.bp.luntan.service.UserService;
import com.bp.luntan.util.ValidationUtil;
import com.google.code.kaptcha.Producer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class AuthController extends BaseController {

    private static final String KAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY";


    @Autowired
    UserService userService;
    @Autowired
    Producer producer;

    @GetMapping("/capthca.jpg")
    public void kaptcha(HttpServletResponse resp) throws IOException {

        // 验证码
        String text = producer.createText();
        BufferedImage image = producer.createImage(text);

        //接受验证码
        httpServletRequest.getSession().setAttribute(KAPTCHA_SESSION_KEY, text);

        //
        resp.setHeader("Cache-Control", "no-store, no-cache");
        //声明图片类型
        resp.setContentType("image/jpeg");
        ServletOutputStream outputStream = resp.getOutputStream();
        //输出图片
        ImageIO.write(image, "jpg", outputStream);
    }

    @GetMapping("/login")
    public String login() {
        return "/auth/login";
    }

    @ResponseBody
    @PostMapping("/login")
    public Result doLogin(String email, String password) {
        //效验邮箱密码是否为空
        if(StrUtil.isEmpty(email) || StrUtil.isBlank(password)) {
            return Result.fail("邮箱或密码不能为空");
        }
        //获取用户名和密码
        UsernamePasswordToken token = new UsernamePasswordToken(email, SecureUtil.md5(password));

        try {
            SecurityUtils.getSubject().login(token);

        } catch (AuthenticationException e) {
            if (e instanceof UnknownAccountException) {
                return Result.fail("用户不存在");
            } else if (e instanceof LockedAccountException) {
                return Result.fail("用户被禁用");
            } else if (e instanceof IncorrectCredentialsException) {
                return Result.fail("密码错误");
            } else {
                return Result.fail("用户认证失败");
            }
        }

        return Result.success().action("/");
    }



    @GetMapping("/register")
    public String register() {
        return "/auth/reg";
    }
    @ResponseBody
    @PostMapping("/register")
    public Result doRegister(User user, String repass, String vercode) {

        //指定效验对象
        ValidationUtil.ValidResult validResult = ValidationUtil.validateBean(user);
        if(validResult.hasErrors()) {
            //result获取错误信息
            return Result.fail(validResult.getErrors());
        }

        if(!user.getPassword().equals(repass)) {
            return Result.fail("两次输入密码不相同");
        }

         String capthca = (String) httpServletRequest.getSession().getAttribute(KAPTCHA_SESSION_KEY);

         System.out.println(capthca);

        if(vercode == null || !vercode.equalsIgnoreCase(capthca)) {
            return Result.fail("验证码输入不正确");
        }

        // 完成注册,action,根据jQuery的form表单跳转到指定页面
        Result result = userService.register(user);
        return result.action("/login");
    }
    @RequestMapping("/user/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/";
    }
}
