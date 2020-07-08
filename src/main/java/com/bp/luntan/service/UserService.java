package com.bp.luntan.service;

import com.bp.luntan.common.lang.Result;
import com.bp.luntan.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bp.luntan.shiro.AccountProfile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bp
 * @since 2020-07-03
 */
public interface UserService extends IService<User> {

    Result register(User user);

    AccountProfile login(String username, String valueOf);
}
