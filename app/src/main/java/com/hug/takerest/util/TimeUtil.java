package com.hug.takerest.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HStan on 2017/4/12.
 */

public class TimeUtil {

    /**
     * String转时间戳
     */
    public static String getTime(String timeString) {
        String timeStamp = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        try {
            d = sdf.parse(timeString);
            long l = d.getTime();
            timeStamp = String.valueOf(l);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStamp;
    }

    public static String getCurrentTime_Today() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new java.util.Date());
    }

    public static String DifferTime(String time, String systime) throws ParseException {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date begin = dfs.parse(time);
        java.util.Date end = dfs.parse(systime);
        long between = (end.getTime() - begin.getTime()) / 1000;//转换成秒
        long day = between / (24 * 3600);
        long hour = between % (24 * 3600) / 3600;
        long minute = between % 3600 / 60;
        long second = between % 60 / 60;
        if (day < 1) {
            if (hour >= 1) {
                return hour + " hour ago";
            } else if (hour < 1) {
                return minute + " minute ago";
            }
        }
        if (day >= 1 && day <= 30) {
            return day + " day ago";
        }
        if (day > 30) {
            if (day <= 3 * 30) {
                return end.getMonth() - begin.getMonth() + " month ago";
            }
        }
        return DateUtil.getYearFromString(time)+ "-" + begin.getMonth() + "-" + begin.getDay();
    }
}
