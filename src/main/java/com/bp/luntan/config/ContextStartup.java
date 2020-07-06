
package com.bp.luntan.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.bp.luntan.entity.Category;
import com.bp.luntan.service.CategoryService;
import com.bp.luntan.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.List;


/**
 * @author b-p
 * @version 1.0
 * @date 2020/7/2 16:59
 */


@Component
public class ContextStartup implements ApplicationRunner, ServletContextAware {

    @Autowired
    CategoryService categoryService;

    ServletContext servletContext;

    @Autowired
    PostService postService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        //查Category
        List<Category> categories = categoryService.list(new QueryWrapper<Category>().eq("status", 0));
        //ftl页面中使用
        servletContext.setAttribute("categorys", categories);
        //初始化本周热议
        postService.initWeekRank();
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
