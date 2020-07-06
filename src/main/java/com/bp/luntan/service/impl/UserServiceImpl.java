package com.bp.luntan.service.impl;

import com.bp.luntan.entity.User;
import com.bp.luntan.mapper.UserMapper;
import com.bp.luntan.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
