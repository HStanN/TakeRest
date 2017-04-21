package com.hug.takerest.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by HStan on 2017/3/29.
 */

public class DateUtil {

    //获取前一天
    public static String getPreviewsDay(Calendar calendar,int amount) {
        calendar.add(Calendar.DAY_OF_MONTH, amount);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String mDateTime = formatter.format(calendar.getTime());
        String date = mDateTime.substring(0, 10);
        return date;
    }

    public static int getYearFromString(String date){
        return Integer.valueOf(date.substring(0, 4));
    }

    public static int getMonthFromString(String date){
        return Integer.valueOf(date.substring(5, 7));
    }

    public static int getDayFromString(String date){
        return Integer.valueOf(date.substring(8, 10));
    }

    /**
     * 获取当前时区时间
     *
     * @param locale 地区
     * */
    public static Calendar getCalendar(Locale locale){
        if (locale == null){
            return Calendar.getInstance(Locale.CHINA);
        }else{
            return Calendar.getInstance(locale);
        }
    }

    /**
     * 根据calendar获取年
     *
     * @param calendar Calendar对象
     * @return year    年
     *
     * */
    public static int getYear(Calendar calendar){
        return calendar.get(Calendar.YEAR);
    }
    /**
     * 根据calendar获取月
     *
     * @param calendar Calendar对象
     * @return month    月
     *
     * */
    public static int getMonth(Calendar calendar){
        return calendar.get(Calendar.MONTH) + 1;
    }
    /**
     * 根据calendar获取日
     *
     * @param calendar Calendar对象
     * @return day    日
     *
     * */
    public static int getDay(Calendar calendar){
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
}
