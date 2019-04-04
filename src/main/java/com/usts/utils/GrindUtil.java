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

    // 获取柱状图信息 dime和dworkighour 同一天的时间要加起来
    public static LinkedHashMap<String,Integer> getHistogramInfo_HourMuchRecords(List<Grinding_Wheel> list){
        LinkedHashMap<String,Integer> info = new LinkedHashMap<>();
        // 要按日期分开
        HashMap<String,List<Grinding_Wheel>> records = divByDate(list);
        for (String key:records.keySet()){
            info.put(key,getDayWorking_HourMuchRecords(records.get(key)));
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

    // 统计一天白班和晚班的工作时长
    public static Tuple getDayShift_HourMuchRecords(List<Grinding_Wheel> list){
        int dayShift = 0;
        int nightShift = 0;
        List<Integer> hours = new ArrayList<>();
        for (Grinding_Wheel wheel:list){
            if (hours.indexOf(wheel.getDhour())==-1){
                //获取一个小时内的最大值
                Grinding_Wheel va = getHourWorkig_HourMuchRecords(list,wheel.getDhour());
                if (va.getDtime().getHours()>=8&&
                        va.getDtime().getHours()<=20){
                    dayShift += va.getDworkinghour();
                }else{
                    nightShift+= va.getDworkinghour();
                }
                hours.add(wheel.getDhour());
            }else {
                continue;
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

    // 过滤得到一天中每个小时的有效信息
    public static List<Grinding_Wheel> filterDayWorkingHour(List<Grinding_Wheel> list){
        ArrayList<Grinding_Wheel> records = new ArrayList<>();
        ArrayList<Integer> hours = new ArrayList<>();
        for (Grinding_Wheel wheel:list){
            if (hours.indexOf(wheel.getDhour())==-1){
                Grinding_Wheel wheel1 = getHourWorkig_HourMuchRecords(list,wheel.getDhour());
                records.add(wheel1);
                hours.add(wheel.getDhour());
            }
        }
        return records;
    }

    //获取一天的在线时长
    public static Integer getDayWorking_HourMuchRecords(List<Grinding_Wheel> selectResult){
        int dayHourWorking = 0;
        List<Integer> hours = new ArrayList<>();
        for (Grinding_Wheel wheel:selectResult){
            if(hours.indexOf(wheel.getDhour()) == -1){
                dayHourWorking += GrindUtil.getHourWorkig_HourMuchRecords(selectResult,wheel.getDhour()).getDworkinghour();
                hours.add(wheel.getDhour());
            }else{
                continue;
            }
        }
        return dayHourWorking;
    }

    // 对应需求2 一个小时多条纪录取最大值
    public static Grinding_Wheel getHourWorkig_HourMuchRecords(List<Grinding_Wheel> list,int hour){
        Grinding_Wheel wheelsMaxWorkingHour = new Grinding_Wheel();
        for (Grinding_Wheel wheel:list){
            if (wheel.getDtime().getHours()==hour){
                if (wheel.getDworkinghour()>=wheelsMaxWorkingHour.getDworkinghour()){
                    wheelsMaxWorkingHour = wheel;
                }
            }
        }
        return wheelsMaxWorkingHour;
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

    public static List<Grinding_Wheel> pageSearch(List<Grinding_Wheel> wheels,int page,int pageSize){
        wheels = removeDuplicate(wheels);
        List<Grinding_Wheel> records = new ArrayList<>();
        if (wheels!=null){
            int size = wheels.size();
            if(size>page*pageSize) {
                records = wheels.subList((page-1)* pageSize, page* pageSize);
            }
            else if(size<=page*pageSize&&size>(page-1) * pageSize){
                records = wheels.subList((page-1) * pageSize, size);
            }else{
                return wheels;
            }
        }
        return records;
    }

    public static List removeDuplicate(List list) {
        HashSet h = new LinkedHashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    public static int isWorkingOff(List<Grinding_Wheel> wheels){
        if (wheels.isEmpty()){
            return 1;
        }
        boolean flag = true;
        for(int i =0;i<wheels.size()-1;i++){
            if (wheels.get(i).getDworkinghour()!=wheels.get(i+1).getDworkinghour()){
             flag = false;
             break;
         }
        }
        if (flag==false){
            return 0;
        }else {
            return 1;
        }
    }
}
