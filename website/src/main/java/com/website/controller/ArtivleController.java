package com.website.controller;

import com.website.domain.Article;
import com.website.service.ArtivleService;
import com.website.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @email :459114302@qq.com
 * @author: Zhu Liangfu
 * @date: 2019/5/15
 * @time: 9:10
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("artivle")
public class ArtivleController {

   @Autowired
    private ArtivleService artivleService;

   /**
    * @author: Zhu Liangfu
    * @Description: 保存文章的编写
    * @date:2019/5/17   9:14
    */
   @RequestMapping("save")
    public Result save (@RequestBody Article article ,@RequestBody  MultipartFile file){
       System.out.println("文章："+article.toString());
       System.out.println("图片"+file.getName());
       return artivleService.save(article,file);
   }

   /**
    * @author: Zhu Liangfu
    * @Description: 查询所有文章
    * @date:2019/5/17   9:14
    */
   @RequestMapping("inquire")
    private List<Article> inquire(){
       return  artivleService.inquire();
   }

   @RequestMapping("singlec")
    private Article singlec(int id){
       return  artivleService.singlec(id);
   }

}
