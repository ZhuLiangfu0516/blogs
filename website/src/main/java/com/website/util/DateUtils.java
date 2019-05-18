package com.website.util;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @email :459114302@qq.com
 * @author: Zhu Liangfu
 * @date: 2019/5/5
 * @time: 15:33
 */
public class DateUtils {

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String FORMATDATE = "yyyy-MM-dd";

    /**
     * @author: Zhu Liangfu
     * @Description:获取单前年月日
     * @date:2019/5/15   11:01
     */
    public static String getFormat(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATDATE);
        return sdf.format(date);
    }

    /**
     * @author: Zhu Liangfu
     * @Description:获取年月日 当前时间
     * @date:2019/5/15   11:00
     */
    public static String getFormatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        return sdf.format(date);
    }

    /**
     * @author: Zhu Liangfu
     * @Description: 判断文件类型是否匹配
     *  * @param null
     * @date:2019/5/8   14:50
     */
    public static boolean isValid(String contentType, String... allowTypes) {
        if (null == contentType || "".equals(contentType)) {
            return false;
        }
        for (String type : allowTypes) {
            if (contentType.indexOf(type) > -1) {
                return true;
            }
        }
        return false;
    }

}
