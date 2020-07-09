package com.bp.luntan.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bp.luntan.entity.UserMessage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bp
 * @since 2020-07-03
 */
public interface UserMessageService extends IService<UserMessage> {
    IPage paging(Page page, QueryWrapper<UserMessage> wrapper);

    void updateToReaded(List<Long> ids);
}
