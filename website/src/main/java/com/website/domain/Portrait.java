package com.website.domain;

import lombok.Data;
import java.io.Serializable;
/**
 * @email :459114302@qq.com
 * @author: Zhu Liangfu - 上传头像
 * @date: 2019/5/5
 * @time: 15:33
 */
@Data
public class Portrait implements Serializable{
    private int id;
    private int imgid;
    private String imgpath;
}
