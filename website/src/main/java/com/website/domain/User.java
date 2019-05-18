package com.website.domain;
import lombok.Data;

import java.io.Serializable;
/**
 * @email :459114302@qq.com
 * @author: Zhu Liangfu
 * @date: 2019/5/5
 * @time: 15:33
 */
@Data
public class User implements Serializable{
   private  int  id;
   private  String code;
   private  String username;
   private  String password;
   private  String registerTime;
   private  String imgpath;
}
