package com.bp.luntan.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bp.luntan.entity.UserMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bp.luntan.vo.UserMessageVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bp
 * @since 2020-07-03
 */
@Component
public interface UserMessageMapper extends BaseMapper<UserMessage> {
    IPage<UserMessageVo> selectMessages(Page page, @Param(Constants.WRAPPER) QueryWrapper<UserMessage> wrapper);
    //注解管理事务
    @Transactional
    @Update("update user_message set status = 1 ${ew.customSqlSegment}")
    void updateToRead(@Param(Constants.WRAPPER)QueryWrapper<UserMessage> wrapper);
}