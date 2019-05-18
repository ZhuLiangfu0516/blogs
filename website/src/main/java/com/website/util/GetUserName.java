package com.website.util;

import org.springframework.security.core.context.SecurityContextHolder;
/**
 * @email :459114302@qq.com
 * @author: Zhu Liangfu
 * @date: 2019/5/5
 * @time: 15:33
 */
public class GetUserName {

    public static  String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
