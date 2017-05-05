package com.hiersun.oohdear.util;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * date 2016/12/12 15:48
 *
 * @author Leon yang_xu@hiersun.com
 * @version V1.0
 */
public class DateUtil {

    public static String getDateStr(Date date, String style){
        SimpleDateFormat simpleDateFormat;
        if(StringUtils.hasText(style)){
            simpleDateFormat = new SimpleDateFormat(style);
        }else{
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        return simpleDateFormat.format(date);
    }


    public static Date getStrDate(String dateStr, String style) throws ParseException {
        SimpleDateFormat simpleDateFormat;
        if(StringUtils.hasText(style)){
            simpleDateFormat = new SimpleDateFormat(style);
        }else{
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        return simpleDateFormat.parse(dateStr);
    }

}
