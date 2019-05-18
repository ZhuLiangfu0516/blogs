package com.website.dao;

import com.website.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * @email :459114302@qq.com
 * @author: Zhu Liangfu
 * @date: 2019/5/5
 * @time: 15:33
 */
public interface UserDao {

    /**
     * @author: Zhu Liangfu
     * @Description: 查询当前用户信息
     * @date:2019/5/5   16:02
     */
    @Select("select * from user  WHERE user.username=#{username}")
    User findUser(String username);

    /**
     * @author: Zhu Liangfu
     * @Description: 保存注册用户
     * @date:2019/5/5   16:03
     */
    @Insert("insert into `user`(username,password,register_time) VALUES (#{username},#{password},#{registerTime}) ")
    void userRegist(User user);


}
