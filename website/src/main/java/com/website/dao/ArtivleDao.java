package com.website.dao;

import com.website.domain.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @email :459114302@qq.com
 * @author: Zhu Liangfu
 * @date: 2019/5/15
 * @time: 9:24
 */

public interface ArtivleDao {


    /**
     * @author: Zhu Liangfu
     * @Description:保存文章
     * @date:2019/5/15   11:21
     */
    @Insert("insert into article(title,antistop,author,describes,articleTime,image,content,catalog) VALUES (#{title},#{antistop},#{author},#{describes},#{articleTime},#{image},#{content},#{catalog})")
    void save(Article article);

    /**
     * @author: Zhu Liangfu
     * @Description:查询所有数据展示页面
     * @date:2019/5/15   13:59
     */
    @Select("SELECT * FROM article")
    List<Article> inquire();

    /**
     * @author: Zhu Liangfu
     * @Description:查询单个记录
     * @date:2019/5/17   10:59
     */
    @Select("SELECT * FROM article WHERE id = #{id}")
    Article singlec(int id);

    /**
     * @Description:浏览量
     * @param id
     */
    @Update("UPDATE article SET  pageview = pageview+1 WHERE id = #{id}")
    void pageviews(int id);
}
