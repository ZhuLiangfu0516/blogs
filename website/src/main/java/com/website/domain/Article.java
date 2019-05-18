package com.website.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @email :459114302@qq.com
 * @author: Zhu Liangfu - 文章
 * @date: 2019/5/15
 * @time: 9:02
 */
@Data
public class Article implements Serializable {

    private int id;
    private String title;
    private String antistop;
    private String author;
    private String describes;
    private String articleTime;
    private String image;
    private String content;
    private int pageview;
    private String enjoy;
    private String catalog;

}
