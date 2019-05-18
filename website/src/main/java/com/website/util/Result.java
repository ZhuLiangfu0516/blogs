package com.website.util;

import lombok.Data;

import java.io.Serializable;
/**
 * @email :459114302@qq.com
 * @author: Zhu Liangfu
 * @date: 2019/5/5
 * @time: 15:33
 */
@Data
public class Result implements Serializable{

    private boolean flag;
    private String code;
    private String message;
    private Object data;

    public Result(boolean flag, String code, String message, Object data) {
        super();
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(boolean flag, String code, String message) {
        super();
        this.flag = flag;
        this.message = message;
        this.code = code;
    }
}
