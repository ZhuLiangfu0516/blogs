package com.website.service;

import com.website.domain.User;
import com.website.util.Result;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @email :459114302@qq.com
 * @author: Zhu Liangfu
 * @date: 2019/5/5
 * @time: 15:33
 */
public interface UserService extends UserDetailsService {

    Result userRegister(User user);

    Result userLogin(User user);
}
