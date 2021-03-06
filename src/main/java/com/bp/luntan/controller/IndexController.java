package com.bp.luntan.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController{

    @RequestMapping({"", "/", "index"})
    public String index() {


        // 1分页信息 2分类 3用户 4置顶  5精选 6排序
        IPage results = postService.paging(getPage(), null, null, null, null, "created");

        //设置分页信息，传入ftl
        httpServletRequest.setAttribute("pageData",results);

        //给ftl传一个值，点击导航时高亮
        httpServletRequest.setAttribute("currentCategoryId",0);
        return "index";
    }
    @RequestMapping("/search")
    public String search(String q) {

        //搜索
        IPage pageData = searchService.search(getPage(), q);

        httpServletRequest.setAttribute("q", q);
        httpServletRequest.setAttribute("pageData", pageData);
        return "search";
    }


}
