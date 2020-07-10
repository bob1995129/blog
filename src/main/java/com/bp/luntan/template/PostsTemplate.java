package com.bp.luntan.template;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bp.luntan.common.templates.DirectiveHandler;
import com.bp.luntan.common.templates.TemplateDirective;
import com.bp.luntan.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostsTemplate extends TemplateDirective {

    @Autowired
    PostService postService;

    @Override
    public String getName() {
        return "posts";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {

        //置顶的内容
        Integer level = handler.getInteger("level");

        Integer pn = handler.getInteger("pn", 1);
        //默认分页大小：3
        Integer size = handler.getInteger("size", 3);
        Long categoryId = handler.getLong("categoryId");
        // 1分页信息 2分类 3用户 4置顶  5精选 6排序
        IPage page = postService.paging(new Page(pn, size), categoryId, null, level, null, "created");

        //传到前端index页面RESULTS=“results”
        handler.put(RESULTS, page).render();
    }
}
