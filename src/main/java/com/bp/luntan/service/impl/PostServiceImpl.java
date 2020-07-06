package com.bp.luntan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bp.luntan.entity.Post;
import com.bp.luntan.mapper.PostMapper;
import com.bp.luntan.service.PostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bp.luntan.vo.PostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bp
 * @since 2020-07-03
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Autowired
    PostMapper postMapper;
    @Override
    public IPage<PostVo> paging(Page page, Long categoryId, Long userId, Integer level, Boolean recommend, String order) {


        if(level == null) level = -1;

        //搜索表单
        QueryWrapper wrapper = new QueryWrapper<Post>()
                .eq(categoryId != null,  "category_id", categoryId)
                .eq(userId != null,  "user_id", userId)
                //level=0筛选非置顶
                .eq(level == 0,  "level", 0)
                .gt(level > 0,  "level", 0)
                .orderByDesc(order != null, order);

        return postMapper.selectPosts(page, wrapper);
    }

    @Override
    public PostVo selectOnePost(QueryWrapper<Post> wrapper) {
        return postMapper.selectOnePost(wrapper);
    }

    @Override
    public void initWeekRank() {

    }

    @Override
    public void incrCommentCountAndUnionForWeekRank(long postId, boolean isIncr) {

    }

    @Override
    public void putViewCount(PostVo vo) {

    }
}
