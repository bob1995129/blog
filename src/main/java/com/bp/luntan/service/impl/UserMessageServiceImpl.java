package com.bp.luntan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bp.luntan.entity.UserMessage;
import com.bp.luntan.mapper.UserMessageMapper;
import com.bp.luntan.service.UserMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bp
 * @since 2020-07-03
 */
@Service
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage> implements UserMessageService {

    @Override
    public IPage paging(Page page, QueryWrapper<UserMessage> wrapper) {
        return null;
    }

    @Override
    public void updateToReaded(List<Long> ids) {

    }
}
