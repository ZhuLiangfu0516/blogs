package com.website.service.impl;

import com.website.dao.ArtivleDao;
import com.website.domain.Article;
import com.website.service.ArtivleService;
import com.website.util.DateUtils;
import com.website.util.Result;
import com.website.util.StatusCode;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * @email :459114302@qq.com
 * @author: Zhu Liangfu
 * @date: 2019/5/5
 * @time: 15:33
 */
@Service
public class ArtivleServiceImpl implements ArtivleService {

    @Autowired
    private ArtivleDao artivleDao;

    private final String[] CONTACT_ALLOW_TYPES = {".gif", "jpeg", ".png", ".jpg"};

    @Override
    public Result save(Article article , MultipartFile file){
        try {
            // 保存图片的路径，图片上传成功后，将路径保存到数据库
            String filePath = "E:/bxdtc/testFile/website/src/main/webapp/images/imgFile/";
            // 获取原始图片的扩展名
            String originalFilename = file.getOriginalFilename();
            System.out.println(originalFilename);
            String iname = originalFilename.substring(originalFilename.length() - 5);
            // 生成文件新的名字
            String newFileName = UUID.randomUUID() + iname;
            File targetFile = new File(filePath, newFileName);
            file.transferTo(targetFile);
            //判断文件格式
            String substring = iname.substring(iname.length() - 4);
            boolean valid = DateUtils.isValid(substring, CONTACT_ALLOW_TYPES);
            if (!valid) {
                targetFile.delete();
                return new Result(false, StatusCode.ERROR, "请上传[ gif，jpg，jpeg，png ]图片做头像!");
            }
            //判断文件的大小
            if (file.getSize() > 1024 * 1024 * 1 || file.getSize() == 0 ) {
                targetFile.delete();
                return new Result(false, StatusCode.ERROR, "图片大于1M,请重新上传！");
            }
            //改变图片的像素
            Thumbnails.of(targetFile).size(500, 500).keepAspectRatio(false).toFile(targetFile);
            article.setImage(newFileName);
            article.setArticleTime(DateUtils.getFormatDate(new Date()));
            artivleDao.save(article);
            return new Result(true, StatusCode.SUCCESS, "文章上传成功！");
        } catch (Exception e) {
            System.out.println("异常报错！");
            return new Result(false, StatusCode.ERROR, "头像上传失败!");
        }
    }

    @Override
    public List<Article> inquire() {
        List<Article> inquire = artivleDao.inquire();
        return inquire;
    }

    @Override
    public Article singlec(int id) {
        Article singlec = artivleDao.singlec(id);
        artivleDao.pageviews(id);
        return singlec;
    }
}
