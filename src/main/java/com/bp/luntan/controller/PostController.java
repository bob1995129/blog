package com.bp.luntan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bp.luntan.entity.Post;
import com.bp.luntan.service.PostService;
import com.bp.luntan.vo.CommentVo;
import com.bp.luntan.vo.PostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author b-p
 * @version 1.0
 * @date 2020/7/2 15:15
 */
@Controller
public class PostController extends BaseController{

    @Autowired
    PostService postService;
    @GetMapping("/category/{id}")
    public String category(@PathVariable(name = "id") Long id){
        //获取当前page
        int pn = ServletRequestUtils.getIntParameter(httpServletRequest, "pn", 1);

        httpServletRequest.setAttribute("currentCategoryId",id);
        httpServletRequest.setAttribute("pn", pn);
        return "post/category";
    }
    @GetMapping("/post/{id}")
    public String detail(@PathVariable(name = "id") Long id){
        //p.id 内容的id
        PostVo vo = postService.selectOnePost(new QueryWrapper<Post>().eq("p.id", id));
        Assert.notNull(vo, "文章已被删除");

        //实时刷新点击量
        postService.putViewCount(vo);

        // 1分页，2文章id，3用户id，排序
        IPage<CommentVo> results = commentService.paing(getPage(), vo.getId(), null, "created");

        httpServletRequest.setAttribute("currentCategoryId", vo.getCategoryId());
        httpServletRequest.setAttribute("post", vo);
        httpServletRequest.setAttribute("pageData", results);

        return "post/detail";
    }
}
