package com.website.service.impl;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.website.dao.UserDao;
import com.website.service.UserService;
import com.website.util.DateUtils;
import com.website.util.Result;
import com.website.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @email :459114302@qq.com
 * @author: Zhu Liangfu
 * @date: 2019/5/5
 * @time: 15:33
 */
@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        redisTemplate.delete("user");
        //去数据库查询用户信息
        com.website.domain.User loginUser = userDao.findUser(username);
        //创建权限对象集合
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        //封装结果
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        user = new User(username, loginUser.getPassword(), authorities);
        redisTemplate.boundValueOps("user").set(username, 5, TimeUnit.MINUTES);

        return user;
    }

    @Override
    public Result userRegister(com.website.domain.User user) {
        String phone = (String) redisTemplate.boundValueOps(user.getUsername()).get();
        String code = (String) redisTemplate.boundValueOps(user.getCode()).get();
        if (StringUtils.isEmpty(code)){
            return new Result(false, StatusCode.ERROR,"验证码过期，请重新获取验证码!");
        }else{
            if (code.equals(user.getCode())) {
                if (userDao.findUser(user.getUsername()) != null) {
                    return new Result(false, StatusCode.ERROR, "用户:" + user.getUsername() + "已存在!");
                }
                if (!user.getUsername().equals(phone)) {
                    return new Result(false, StatusCode.ERROR, "用户名不正确,请输入正确的用户名!");
                }
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setRegisterTime(DateUtils.getFormatDate(new Date()));
                userDao.userRegist(user);

                redisTemplate.delete(phone);
                redisTemplate.delete(code);
                return new Result(true, StatusCode.SUCCESS, "恭喜您注册成功,即将跳转去登录!");
            } else {

                return new Result(false, StatusCode.ERROR,  "您输入的验证码不正确!");
            }
        }
    }

    @Override
    public Result userLogin(com.website.domain.User user) {
        try {
            //根据用户输入信息去数据库搜索
            com.website.domain.User loginUser = userDao.findUser(user.getUsername());
            if (loginUser == null) {
                //如果数据库没查询到用户,则返回用户输入的用户名
                System.out.println(user.getUsername() + "用户不存在");
                return new Result(false, StatusCode.ERROR, user.getUsername() + "不存在,请先注册再登录!");
            }
            //如果登录输入的密码跟数据库的密码不匹配,则返回密码错误
            if (!passwordEncoder.matches(user.getPassword(),loginUser.getPassword())) {
                return new Result(false, StatusCode.ERROR, "密码不正确,请输入正确的密码!");
            }
            //校验成功
            return new Result(true, StatusCode.SUCCESS, "登录成功!");
        } catch (Exception e) {
            e.printStackTrace();
            //如果发生异常,则给用户友好提示
            return new Result(false, StatusCode.ERROR, "服务器繁忙,登录失败!");
        }

    }



}
