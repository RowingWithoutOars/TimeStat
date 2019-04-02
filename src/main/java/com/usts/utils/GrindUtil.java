package com.usts.utils;

import com.usts.model.Grinding_Wheel;
import com.usts.model.Tuple;

import java.util.*;

public class GrindUtil {
    // 获取柱状图信息 dime和dworkighour 同一天的时间要加起来
    public static LinkedHashMap<String,Integer> getHistogramInfo(List<Grinding_Wheel> list){
        LinkedHashMap<String,Integer> info = new LinkedHashMap<>();
        for (Grinding_Wheel wheel:list){
            String time = wheel.getDtime().toLocaleString().split(" ")[0];
            if (info.get(time)==null){
                info.put(time,wheel.getDworkinghour());
            }else{
                int workighour = info.get(time);
                workighour += wheel.getDworkinghour();
                info.put(time,workighour);
            }
        }
        return info;
    }

    // 统计单个柱状图信息
    public static LinkedHashMap<String,Integer> getHistogramSingleInfo(List<Grinding_Wheel> list){
        LinkedHashMap<String,Integer> info = new LinkedHashMap<>();
        for(Grinding_Wheel wheel:list){
            String time = wheel.getDtime().getHours()+"";
            int workhour = wheel.getDworkinghour();
            info.put(time,workhour);
        }
        return info;
    }

    //按照日期将所有值分开
    public static HashMap<String,List<Grinding_Wheel>> divByDate(List<Grinding_Wheel> wheels){
        HashMap<String,List<Grinding_Wheel>> info = new LinkedHashMap();
        for (Grinding_Wheel wheel:wheels){
            String date = wheel.getDtime().toLocaleString().split(" ")[0];
            if (info.get(date)!=null){
                info.get(date).add(wheel);
            }else{
                info.put(date,new ArrayList<Grinding_Wheel>());
                info.get(date).add(wheel);
            }
        }
        return info;
    }

    // 统计一天白班和晚班的工作时长
    public static Tuple getDayShift(List<Grinding_Wheel> list){
        int dayShift = 0;
        int nightShift = 0;
        for (Grinding_Wheel wheel:list){
            if (wheel.getDtime().getHours()>=8&&
                    wheel.getDtime().getHours()<=20){
                dayShift += wheel.getDworkinghour();
            }else{
                nightShift+= wheel.getDworkinghour();
            }
        }
        return new Tuple(dayShift,nightShift);
    }

    //具体某天某自然小时设备工作了多长时间
    public static Grinding_Wheel getHourWorkig(List<Grinding_Wheel> list,int hour){
        for (Grinding_Wheel wheel:list){
            if (wheel.getDtime().getHours()==hour){
                return wheel;
            }
        }
        return null;
    }

    // 传入一个时间点，转化为时间段
    public static String hourConverTime(int hour){
        String time = "";
        int endHour = hour+1;
        time = hour+":00-"+endHour+":00";
        return time;
    }

    // 将秒转化为分钟
    public static String converToMin(int workighour){
        String time = "";
        time += workighour/60 + "m";
        time += workighour%60 + "s";
        return time;
    }

    // 将秒转化为00:00:00
    public static String converToHour(int workighour){
        String time = "";
        if(workighour/3600!=0){
            time = workighour/3600+"h";
        }
        time += (workighour%3600)/60+"m";
        time += (workighour%3600)%60+"m";
        return time;
    }

    public static List<Tuple> label0(List<Grinding_Wheel> wheels){
        List<Tuple> info = new ArrayList<>();
        for (Grinding_Wheel wheel:wheels){
            String time = wheel.getDtime().toLocaleString().split(" ")[0];
            Tuple tuple = new Tuple(time+"号"+GrindUtil.hourConverTime(wheel.getDhour()),GrindUtil.converToMin(wheel.getDworkinghour()));
            info.add(tuple);
        }
        return info;
    }
    public static List<Tuple> label1(List<Grinding_Wheel> wheels){
        //按照时间将所有纪录分开，方便统计白班和晚班
        HashMap<String,List<Grinding_Wheel>> info = GrindUtil.divByDate(wheels);
        List<Tuple> dayShiftNight = new ArrayList<>();
//        HashMap<String,Tuple> dayShiftNight = new HashMap<>();
        for(String key:info.keySet()){
            Tuple tmp = GrindUtil.getDayShift(info.get(key));
            dayShiftNight.add(new Tuple(key+"白班",GrindUtil.converToHour(Integer.parseInt(tmp.getName().toString()))));
            dayShiftNight.add(new Tuple(key+"晚班",GrindUtil.converToHour(Integer.parseInt(tmp.getValue().toString()))));
        }
        return dayShiftNight;
    }
}
