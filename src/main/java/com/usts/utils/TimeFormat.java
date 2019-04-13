package com.usts.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeFormat {
    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static Date strToDate1(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    // 得到下一天
    public static Date getNextDate(String startTime){
        Date date = TimeFormat.strToDate(startTime);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH,1);
        Date end_Time = c.getTime();
        return end_Time;
    }
    public static Date getNDate(String startTime,int days){
        Date date = TimeFormat.strToDate(startTime);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH,days);
        Date end_Time = c.getTime();
        return end_Time;
    }

    public static Date getPreDate(String startTime){
        Date date = TimeFormat.strToDate(startTime);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH,-1);
        Date end_Time = c.getTime();
        return end_Time;
    }

    //得到下一天，字符串形式
    public static String getNextDateStr(String startTime){
        Date nextDate = getNextDate(startTime);
        return getFormatTime(nextDate);
    }

    public static String getFormatTime(Date date){
        SimpleDateFormat myFmt2=new SimpleDateFormat("yyyy-MM-dd");
        return myFmt2.format(date);
    }

    public static String getDateIgnoreUTC(String str){
        str = str.replace("Z"," UTC");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = format.parse(str);
        }catch (Exception e){
            return null;
        }
        return getFormatTime(date);
    }

    public static String isWorkDay(Date date){
        String dateStr = getFormatTime(date);
        Date startTime = strToDate1(dateStr+" 07:59:59");
        Date endTime = strToDate1(getNextDateStr(dateStr)+" 08:00:00");
        if (startTime.before(date)&&endTime.after(date)){
            return dateStr;
        }
        return getFormatTime(getPreDate(dateStr));
    }

    public static void main(String args[]){
        System.out.println(getNextDateStr("2019-4-11"));
    }
}

