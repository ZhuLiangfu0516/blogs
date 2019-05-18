package com.website.service.impl;

import com.website.dao.PortraitDao;
import com.website.dao.UserDao;
import com.website.domain.Portrait;
import com.website.domain.User;
import com.website.service.PortraitService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;


/**
 * @email :459114302@qq.com
 * @author: Zhu Liangfu
 * @date: 2019/5/5
 * @time: 15:33
 */
@Service
public class PortraitServiceImpl implements PortraitService {
    @Autowired
    private  PortraitDao portraitDao;
    @Autowired
    private UserDao userDao;

    @Override
    public User userImage(String name) {
        try {
            User user = userDao.findUser(name);
            Portrait portrait = portraitDao.allUser(user.getId());
            if (StringUtils.isEmpty( portrait)){
                user.setImgpath("../images/moren.jpg");
                return user;
            }
            File filePath = new File("E:/java_development/apache-tomcat-9.0.10/webapps/index/images/" + portrait.getImgpath());
            if (!filePath.exists()) {
                user.setImgpath("../images/moren.jpg");
                return user;
            }
            user.setImgpath("../images/" + portrait.getImgpath());
            return user;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
