package com.bp.luntan.template;

import com.bp.luntan.common.templates.DirectiveHandler;
import com.bp.luntan.common.templates.TemplateDirective;
import com.bp.luntan.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 本周热议
 */
@Component
public class HotsTemplate extends TemplateDirective {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public String getName() {
        //返回热议
        return "hots";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        String weekRankKey = "week:rank";

        Set<ZSetOperations.TypedTuple> typedTuples = redisUtil.getZSetRank(weekRankKey, 0, 6);

        List<Map> hotPosts = new ArrayList<>();

        for (ZSetOperations.TypedTuple typedTuple : typedTuples) {
            Map<String, Object> map = new HashMap<>();

            Object value = typedTuple.getValue(); // post的id
            String postKey = "rank:post:" + value;

            map.put("id", value);
            map.put("title", redisUtil.hget(postKey, "post:title"));
            map.put("commentCount", typedTuple.getScore());//获取合并后的总评数量

            //放到集合
            hotPosts.add(map);
        }

        //传到前端
        handler.put(RESULTS, hotPosts).render();

    }
}
