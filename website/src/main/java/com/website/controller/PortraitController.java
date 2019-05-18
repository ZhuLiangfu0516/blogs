package com.website.controller;

import com.website.dao.PortraitDao;
import com.website.dao.UserDao;
import com.website.domain.Portrait;
import com.website.domain.User;
import com.website.service.PortraitService;
import com.website.util.DateUtils;
import com.website.util.GetUserName;
import com.website.util.Result;
import com.website.util.StatusCode;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * @email :459114302@qq.com
 * @author: Zhu Liangfu
 * @date: 2019/5/5
 * @time: 15:33
 */
@SuppressWarnings("All")
@RestController
@RequestMapping("portrait")
public class PortraitController {

    @Autowired
    private PortraitDao portraitDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PortraitService portraitService;

    private final String[] CONTACT_ALLOW_TYPES = {".gif", "jpeg", ".png", ".jpg"};

    @RequestMapping("upload")
    public Result upload(Portrait portrait, MultipartFile file) {
        try {
            User user = userDao.findUser(GetUserName.getUserName());
            Portrait prt = portraitDao.allUser(user.getId());
            // 保存图片的路径，图片上传成功后，将路径保存到数据库
            String filePath = "E:/java_development/apache-tomcat-9.0.10/webapps/index/images/";
            // 获取原始图片的扩展名
            String originalFilename = file.getOriginalFilename();
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

            //操作
            if (StringUtils.isEmpty(prt) && file != null) {
                portrait.setImgid(user.getId());
                portrait.setImgpath(newFileName);
                portraitDao.addUser(portrait);
                return new Result(true, StatusCode.SUCCESS, "头像上传成功!");
            } else if (!StringUtils.isEmpty(prt) && file != null) {
                File files = new File(filePath + prt.getImgpath());
                files.delete();
                portrait.setImgid(user.getId());
                portrait.setImgpath(newFileName);
                portraitDao.uploadimg(portrait);
                return new Result(true, StatusCode.SUCCESS, "头像更新成功!");
            } else {
                targetFile.delete();
                return new Result(false, StatusCode.ERROR, "头像上传失败!");
            }
        } catch (Exception e) {
            System.out.println("异常报错！");
            return new Result(false, StatusCode.ERROR, "头像上传失败!");
        }
    }

    @RequestMapping("imgUser")
    public User toImage() {
        return portraitService.userImage(GetUserName.getUserName());
    }

}

