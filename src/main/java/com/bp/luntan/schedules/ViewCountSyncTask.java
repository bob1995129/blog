package com.bp.luntan.schedules;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bp.luntan.entity.Post;
import com.bp.luntan.service.PostService;
import com.bp.luntan.util.RedisUtil;
import com.bp.luntan.vo.PostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 【评论同步至数据库】主启动需添加@EnableScheduling
 */
@Component
public class ViewCountSyncTask {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    PostService postService;


    @Scheduled(cron = "0/5 * * * * *") //每分钟同步
    public void task() {

        Set<String> keys = redisTemplate.keys("rank:post:*");

        List<String> ids = new ArrayList<>();
        //获取所有评论的数量
        for (String key : keys) {
            //如果评论存在就放到list去
            if(redisUtil.hHasKey(key, "post:viewCount")){
                ids.add(key.substring("rank:post:".length()));
            }
        }

        if(ids.isEmpty()) return;

        // 需要更新阅读量
        List<Post> posts = postService.list(new QueryWrapper<Post>().in("id", ids));

        posts.stream().forEach((post) ->{
            Integer viewCount = (Integer) redisUtil.hget("rank:post:" + post.getId(), "post:viewCount");
            post.setViewCount(viewCount);
        });

        if(posts.isEmpty()) return;

        boolean isSucc = postService.updateBatchById(posts);

        if(isSucc) {
            ids.stream().forEach((id) -> {
                redisUtil.hdel("rank:post:" + id, "post:viewCount");
                System.out.println("文章"+id + "->同步成功。"+"时间："+new Date());
            });
        }
    }

}
