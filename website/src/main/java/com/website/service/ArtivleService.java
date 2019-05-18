package com.website.service;

import com.website.domain.Article;
import com.website.util.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @email :459114302@qq.com
 * @author: Zhu Liangfu
 * @date: 2019/5/15
 * @time: 9:14
 */
public interface ArtivleService {

    Result save(Article article ,MultipartFile file);

    List<Article> inquire();

    Article singlec(int id);
}
