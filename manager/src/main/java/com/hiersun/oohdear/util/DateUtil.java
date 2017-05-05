package com.hiersun.oohdear.util;/**
 * Copyright © 2015 hiersun Holdings Limited.
 * All rights reserved.
 * @Author      lanbery
 * @Date        2015年9月24日
 * @Anotation
 */


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author caiweidong
 *
 */
public class DateUtil {
    /**
     * 标准格式 yyyy-MM-dd
     */
    public static final String STANDARD_FORMAT = "yyyy-MM-dd";
    /** yyyy-MM-dd HH:mm:ss */
    public static final String EN_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * Mysql 时间戳 格式：yyyy-MM-dd HH:mm:ss.S
     */
    public static final String MYSQL_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
    /**
     * 20150101 格式
     */
    public static final String YMD8_FORMAT = "yyyyMMdd";

    /**
     * 取得格式化日期字符串，格式2013年-11月-8日
     */
    public String getFormateDate(Date date) {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy年MM月dd日");
        String strDate = dateformat.format(date);
        return strDate;
    }

    /**
     * 获取当前日期 :2015-01-13
     *
     * @Author :wdcai
     * @Comment :
     * @return
     */
    public static String getCurrDate() {
        String format = "yyyy-MM-dd";
        return getCurrDate(format);
    }

    /**
     * 获取当前时间 :2015-01-13 10:22:19.386
     *
     * @Author :wdcai
     * @Comment :
     * @return
     */
    public static String getCurrTime() {
        String format = "yyyy-MM-dd HH:mm:ss.SSS";
        return DateUtil.getCurrDate(format);
    }

    /**
     *
     * @Comments :
     * @Author :wdcai
     * @Version :V1.0.0
     * @return :Timestamp current
     */
    public static Timestamp getTimestamp() {
        Date d = new Date();
        return new Timestamp(d.getTime());
    }

    /**
     *
     * @Author :wdcai
     * @Comment :
     * @param format
     * @return
     */
    public static String getCurrDate(String format) {
        Date d = new Date();
        return formatDate(d, format);
    }

    /**
     *
     * @Author :wdcai
     * @Comment :
     * @return :date
     */
    public static Date currDateTime() {
        Date date = new Date();
        return date;
    }

    /**
     *
     * @Author :wdcai
     * @Comment :
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        if (date == null)
            return "";
        SimpleDateFormat dateformat = new SimpleDateFormat(format);
        return dateformat.format(date);
    }

    /**
     *
     * @Author :wdcai
     * @Comment :将yyyy-MM-dd HH:mm:ss.S 默认格式日期字符串转成 yyyy-MM-dd
     * @param timeStr
     *            :yyyy-MM-dd HH:mm:ss.S 默认
     * @return
     */
    public static String convertDBDatetimeStr(String timeStr) {
        return convertDBDatetimeStr(timeStr, MYSQL_DATETIME_FORMAT);
    }

    /**
     *
     * @Author :wdcai
     * @Comment :将指定格式日期字符串转成 yyyy-MM-dd
     * @param timeStr
     *            :yyyy-MM-dd HH:mm:ss.S
     * @param format
     *            :
     * @return
     */
    public static String convertDBDatetimeStr(String timeStr, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            Date date = df.parse(timeStr);
            return formatDate(date, STANDARD_FORMAT);
        } catch (ParseException e) {
            if (timeStr.length() > 10) {
                return timeStr.substring(0, 10);
            } else {
                return timeStr;
            }
        }
    }

    /**
     * @Author :wdcai
     * @Comment :
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {
        //
        String ds = "2015-01-07 20:42:41.0";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date d = df.parse(ds);
        System.out.println(formatDate(d, "yyyy-MM-dd"));

        // String s = DateUtil.getCurrDate();
        // String ss = DateUtil.getCurrTime();
        // System.out.println(s);
        // System.out.println(ss);
    }

    /**
     *
     * @Comments :获取几天后的日期
     * @Author :shuolid
     * @Version :V1.0.0
     * @return
     */
    public static String getDaysLater(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return formatDate(calendar.getTime(), "yyyy-MM-dd") + " 00:00:00";
    }
    /**
     *
     * @Author      lanbery
     * @Version		v1.0.0
     * @Date        2016年6月8日
     * @return		2016
     */
    public static String getYear(){
        String format = "yyyy";
        return getCurrDate(format);
    }
    /**
     *
     * @Author      lanbery
     * @Version		v1.0.0
     * @Date        2016年6月8日
     * @return
     */
    public static String getYear2(){
        String format = "yy";
        return getCurrDate(format);
    }

    /**
     *
     * @Comments :获取几个月后的日期
     * @Author :shuolid
     * @Version :V1.0.0
     * @return
     */
    public static String getMonthsLater(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, month);
        return formatDate(calendar.getTime(), "yyyy-MM-dd") + " 23:59:59";
    }
}
