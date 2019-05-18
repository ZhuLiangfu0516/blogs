package com.website.dao;

import com.website.domain.Portrait;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @email :459114302@qq.com
 * @author: Zhu Liangfu
 * @date: 2019/5/5
 * @time: 15:33
 */
public interface PortraitDao {

    /**
     * @author: Zhu Liangfu
     * @Description: 根据用户信息保存头像
     * @date:2019/5/5 16:03
     */
    @Insert("insert into portrait(img_id,img_path) values(#{imgid},#{imgpath})")
    void addUser(Portrait portrait);

    /**
     * @author: Zhu Liangfu
     * @Description: 根据用户id拿取头像信息
     * @date:2019/5/5 16:04
     */
    @Select("select * from portrait where portrait.img_id = #{id}")
    Portrait allUser(int id);

    /**
     * @author: Zhu Liangfu
     * @Description: 更新已有头像
     * @date:2019/5/5 16:04
     */
    @Update("update portrait p set p.img_path = #{imgpath} where p.img_id = #{imgid}")
    void uploadimg(Portrait portrait);
}
