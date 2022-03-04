package com.exc.roadlamp.utils;

import com.xuexiang.xutil.common.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateUtil {



    /*
     * 将时间转换为时间戳
     */
    public static long dateToStampOnlyDay(String s) throws ParseException {

        if(StringUtils.isEmpty(s)){
            return System.currentTimeMillis();
        }



        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();

        return ts;
//        res = String.valueOf(ts);
//        return res;
    }


    public static long dateToStampOnlyDay2(String s) throws ParseException {

        if(StringUtils.isEmpty(s)){
            return System.currentTimeMillis();
        }



        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();

        return ts;
//        res = String.valueOf(ts);
//        return res;
    }

}
