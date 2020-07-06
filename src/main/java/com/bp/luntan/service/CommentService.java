package com.bp.luntan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bp.luntan.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bp.luntan.vo.CommentVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bp
 * @since 2020-07-03
 */
public interface CommentService extends IService<Comment> {

    IPage<CommentVo> paing(Page page, Long postId, Long userId, String order);


}
