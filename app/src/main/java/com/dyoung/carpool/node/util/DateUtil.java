package com.dyoung.carpool.node.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 2016/11/21.
 */
public class DateUtil {
    /**
     * 时间转换器
     *
     */

    /**
     * 时间戳转换为字符串
     * @param currentTime
     * @return
     * @throws ParseException
     */
    public static  String formatToDay(Long currentTime){
        Date date=new Date();
        date.setTime(currentTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }

    /**
     * 时间戳转换为完整的日期字符中格式
     * @param currentTime
     * @return
     * @throws ParseException
     */
    public static  String formatWholeDate(Long currentTime){
        Date date=new Date();
        date.setTime(currentTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 字符串转换为日期格式
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Long parse(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(strDate).getTime();
    }

}
