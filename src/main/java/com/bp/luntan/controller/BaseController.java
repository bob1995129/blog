package com.bp.luntan.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bp.luntan.service.CommentService;
import com.bp.luntan.service.PostService;
import com.bp.luntan.service.UserMessageService;
import com.bp.luntan.service.UserService;
import com.bp.luntan.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author b-p
 * @version 1.0
 * @date 2020/7/3 11:47
 */
public class BaseController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserMessageService messageService;


    //设置pageNumber ，默认值1
    public Page getPage(){
        int pn = ServletRequestUtils.getIntParameter(httpServletRequest,"pn",1);

        int size = ServletRequestUtils.getIntParameter(httpServletRequest,"size",3);
       return new Page(pn,size);
    }

    //获取用户信息
    protected AccountProfile getProfile() {
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getProfileId() {
        return getProfile().getId();
    }

}
