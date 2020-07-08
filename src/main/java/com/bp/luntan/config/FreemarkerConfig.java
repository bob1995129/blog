package com.bp.luntan.config;

import com.bp.luntan.template.HotsTemplate;
import com.bp.luntan.template.PostsTemplate;
import com.bp.luntan.template.TimeAgoMethod;
import com.jagregory.shiro.freemarker.ShiroTags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class FreemarkerConfig {

    @Autowired
    private freemarker.template.Configuration configuration;

    @Autowired
    PostsTemplate postsTemplate;

    @Autowired
    HotsTemplate hotsTemplate;


    @PostConstruct
    public void setUp() {
        configuration.setSharedVariable("timeAgo", new TimeAgoMethod());
        configuration.setSharedVariable("posts", postsTemplate);

        //页面标签
        configuration.setSharedVariable("hots",hotsTemplate);
        configuration.setSharedVariable("shiro",new ShiroTags());
    }

}
