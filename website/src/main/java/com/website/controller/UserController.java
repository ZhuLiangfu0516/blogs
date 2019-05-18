package com.website.controller;

import com.website.domain.User;
import com.website.service.UserService;
import com.website.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
/**
 * @email :459114302@qq.com
 * @author: Zhu Liangfu
 * @date: 2019/5/5
 * @time: 15:33
 */
@SuppressWarnings("All")
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("regist")
    public Result register(@RequestBody User user){
        return userService.userRegister(user);
    }

    @RequestMapping("login")
    public Result login(@RequestBody User user){
        return  userService.userLogin(user);
    }


}
