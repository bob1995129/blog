package com.bp.luntan.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bp.luntan.common.lang.Result;
import com.bp.luntan.entity.User;
import com.bp.luntan.mapper.UserMapper;
import com.bp.luntan.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bp.luntan.shiro.AccountProfile;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bp
 * @since 2020-07-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Result register(User user) {
        //效验得到的email是不是已注册
        int count = this.count(new QueryWrapper<User>()
                .eq("email", user.getEmail())
                .or()
                .eq("username", user.getUsername())
        );
        if(count > 0) return Result.fail("用户名或邮箱已被占用");

        //System.out.println(System.getProperty("user.timezone"));

        //存储用户的信息到数据库
        User temp = new User();
        temp.setUsername(user.getUsername());
        temp.setPassword(SecureUtil.md5(user.getPassword()));
        temp.setEmail(user.getEmail());
        temp.setAvatar("/images/avatar/default.png");

        temp.setCreated(new Date());
        temp.setPoint(0);
        temp.setVipLevel(0);
        temp.setCommentCount(0);
        temp.setPostCount(0);
        temp.setGender("0");
        this.save(temp);

        return Result.success();
    }

    @Override
    public AccountProfile login(String email, String password) {

        //获取前端页面写入的email和密码
        User user = this.getOne(new QueryWrapper<User>().eq("email", email));
        if(user == null) {
            throw new UnknownAccountException();
        }
        if(!user.getPassword().equals(password)){
            throw new IncorrectCredentialsException();
        }

        user.setLasted(new Date());

        this.updateById(user);

        //定义个人信息
        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(user, profile);
        //将拿到的个人信息复制到profile
        return profile;
    }
}
