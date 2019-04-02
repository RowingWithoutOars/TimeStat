package com.usts.controller;

import com.usts.model.*;
import com.usts.service.IGrindService;
import com.usts.utils.ExportExcel;
import com.usts.utils.GrindUtil;
import com.usts.utils.TimeFormat;
import com.usts.utils.TypeConvertTableName;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.*;

@Controller
@RequestMapping("/data")
public class GrindController {

    @Autowired
    private IGrindService grindService;

    // 对应需求2
    @RequestMapping(value = "/searchWorkingHour", produces = "application/json; charset=utf-8")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public DataResult searchWorkingHour(@RequestBody Map map) {
//        Grinding_Wheel wheel = new Grinding_Wheel();
        QueryRo queryRo = new QueryRo();
        DataResult dataResult = new DataResult();
        try {
            int type = Integer.parseInt(map.get("type").toString());// 表
            int hour = Integer.parseInt(map.get("hour").toString());// 小时
            String startTime = map.get("date").toString();// 日期
//        int type = Integer.parseInt("1");// 表
//        int hour = Integer.parseInt("2");// 小时
//        String startTime = "2019-4-1";// 日期
            queryRo.setTable_name(TypeConvertTableName.getTable_Name(type));//设置表名
            queryRo.setStartTime(startTime);
            queryRo.setEndTime(TimeFormat.getNextDate(startTime).toLocaleString());//设置为一天的
            List<Grinding_Wheel> wheels = grindService.slectWheelStatus(queryRo);
            dataResult.setData(GrindUtil.getHourWorkig(wheels,hour));
            dataResult.setCode(200);
//            throw new Exception("报错信息测试");
//            System.out.println("========================="+GrindUtil.getHourWorkig(wheels,hour));
        }catch (Exception e){
            dataResult.setCode(500);
            dataResult.setMsg(e.toString());
        }
        return dataResult;
    }

    // 对应需求3
    @RequestMapping(value = "/searchWorkingStatus", produces = "application/json; charset=utf-8")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public DataResult searchWorkingStatus(){
        Map<String,WheelStatus> info = new HashMap();
        QueryRo queryRo = new QueryRo();
        DataResult dataResult = new DataResult();
        try {
            ArrayList<String> table_name = TypeConvertTableName.getAllTableName();
            HashMap<String, Grinding_Wheel> status = new LinkedHashMap<>();
            HashMap<String, Integer> allTime = new LinkedHashMap<>();
            HashMap<String, Integer> dayTime = new LinkedHashMap<>();
            for (String name : table_name) {
                queryRo.setTable_name(name);// 设置表名
                status.put(name, grindService.selectStatusNew(queryRo));
                allTime.put(name, grindService.selectAllWorkHour(queryRo));
                dayTime.put(name, grindService.selectDayWorkHour(queryRo));
            }
            for (String key : status.keySet()) {//输出八个设备的当前工作状态
                WheelStatus wheelStatus = new WheelStatus();
                wheelStatus.setStatus(status.get(key).getDstating());// 设备状态
                wheelStatus.setAll_work(allTime.get(key));// 设备
                wheelStatus.setCurrent_day_work(dayTime.get(key));
                wheelStatus.setCurrent_hour_work(status.get(key).getDworkinghour());
                info.put(key, wheelStatus);
            }
            dataResult.setData(info);
            dataResult.setCode(200);
        }catch (Exception e){
            dataResult.setCode(500);
            dataResult.setMsg(e.toString());
        }
        return dataResult;
    }

    //需求4 需要传入时间段
    @RequestMapping(value = "/serachTimeSlotStatus", produces = "application/json; charset=utf-8")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public Map serachTimeSlotStatus(@RequestBody Map map){
        Map result = new HashMap();
        QueryRo queryRo = new QueryRo();
        int type = Integer.parseInt(map.get("type").toString());// 表
        String startTime = map.get("start").toString();// 开始时间
        String endTime = map.get("end").toString(); // 结束时间
//        int type = 1;
//        String startTime = "2019-3-31";
//        String endTime = "2019-4-3";
        queryRo.setTable_name(TypeConvertTableName.getTable_Name(type));
        queryRo.setStartTime(startTime);
        queryRo.setEndTime(endTime);
        List<Grinding_Wheel> lis = grindService.slectWheelStatus(queryRo);// 符合时间段的所有数据库数据
        HashMap<String,Integer> info = GrindUtil.getHistogramInfo(lis);
        HashMap<String,Integer> perDayWorkingTime = new LinkedHashMap<>(); // 时间段内每天的工作时间
        HashMap<String,HashMap<String,Integer>> singleHourWoring = new HashMap<>();//单个小时的数据
        for(String s : info.keySet()){
            perDayWorkingTime.put(s,info.get(s));
        }
        HashMap<String,List<Grinding_Wheel>> dateInfo = GrindUtil.divByDate(lis);//按照日期将信息分开
        for (String s:dateInfo.keySet()){
            HashMap<String,Integer> single = new HashMap<>();
            single = GrindUtil.getHistogramSingleInfo(dateInfo.get(s));//统计一天的
            singleHourWoring.put(s,single);//纪录
        }
        result.put("data",perDayWorkingTime);
        result.put("day",singleHourWoring);
        return result;
    }

    //需求4.1 需要传入时间段 和表名 查询单个的设备工作时间
    @RequestMapping(value = "/serachDayWorkTime", produces = "application/json; charset=utf-8")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
//    @RequestBody Map map
    public DataResult serachDayWorkTime(@RequestBody Map map){
        DataResult dataResult = new DataResult();
        QueryRo queryRo = new QueryRo();
        try {
            int type = Integer.parseInt(map.get("type").toString());// 表
        String startTime = map.get("start").toString();// 开始时间
        String endTime = map.get("end").toString(); // 结束时间
            queryRo.setTable_name(TypeConvertTableName.getTable_Name(type));
            queryRo.setStartTime(startTime);
            queryRo.setEndTime(endTime);
            List<Grinding_Wheel> lis = grindService.slectWheelStatus(queryRo);// 符合时间段的所有数据库数据
            HashMap<String, Integer> info = GrindUtil.getHistogramInfo(lis);
            LinkedPicInfo picInfo = new LinkedPicInfo();
            for (String s : info.keySet()) {
                picInfo.getX().add(s);
                picInfo.getY().add(info.get(s));
            }
            dataResult.setData(picInfo);
            dataResult.setCode(200);
        }catch (Exception e){
            dataResult.setCode(500);
            dataResult.setMsg(e.toString());
        }
        return dataResult;
    }

    //需求4.2 需要传入时间段 和表名 查询单个的设备一天自然小时的工作时间
    @RequestMapping(value = "/serachHourWork", produces = "application/json; charset=utf-8")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public DataResult serachHourWork( @RequestBody Map map){
        DataResult dataResult = new DataResult();
        QueryRo queryRo = new QueryRo();
        try {
        int type = Integer.parseInt(map.get("type").toString());// 表
        String startTime = map.get("start").toString();// 开始时间
//        String endTime = map.get("end").toString(); // 结束时间
//            int type = 1;
//            String startTime = "2019-3-31";
            String endTime = TimeFormat.getNextDate(startTime).toLocaleString().split(" ")[0];
            queryRo.setTable_name(TypeConvertTableName.getTable_Name(type));
            queryRo.setStartTime(startTime);
            queryRo.setEndTime(endTime);
            List<Grinding_Wheel> lis = grindService.slectWheelStatus(queryRo);// 符合时间段的所有数据库数据
            LinkedPicInfo picInfo = new LinkedPicInfo();
            for(Grinding_Wheel wheel:lis){
                picInfo.getX().add(wheel.getDhour());
                picInfo.getY().add(wheel.getDworkinghour());
            }
            dataResult.setData(picInfo);
            dataResult.setCode(200);
        }catch (Exception e){
            dataResult.setCode(500);
            dataResult.setMsg(e.toString());
        }
        return dataResult;
    }



    //需求5 传入时间段
    @RequestMapping(value = "/searchDayShift", produces = "application/json; charset=utf-8")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public Map searchDayShift( @RequestBody Map map){
        QueryRo queryRo = new QueryRo();
        String startTime = map.get("start").toString();
        String endTime = map.get("end").toString();
//        String startTime = "2019-4-1";
//        String endTime = "2019-4-3";
        queryRo.setStartTime(startTime);
        queryRo.setEndTime(endTime);
        ArrayList<String> table_name = TypeConvertTableName.getAllTableName();
        HashMap<String,HashMap<String,Tuple>> tuples = new LinkedHashMap<>();
        HashMap<String,HashMap<String,List<Grinding_Wheel>>> info = new LinkedHashMap<>();
        for (String name:table_name){
            queryRo.setTable_name(name);// 设置表名
            List<Grinding_Wheel> wheels = grindService.slectWheelStatus(queryRo);
            HashMap<String,List<Grinding_Wheel>> infoChild = GrindUtil.divByDate(wheels);
            info.put(name,infoChild);
            HashMap<String,Tuple> tupleHashMap = new LinkedHashMap<>();
            for(String date:infoChild.keySet()){
                tupleHashMap.put(date,GrindUtil.getDayShift(wheels));
            }
            tuples.put(name,tupleHashMap);
        }
        HashMap<String,HashMap<String,Tuple>> records = new HashMap();
        for (String s:tuples.keySet()){// 第一层是设备名
            HashMap<String,Tuple> singleWheel = new HashMap<>();
            for (String date:tuples.get(s).keySet()){// 第二层是日期
                singleWheel.put(date,tuples.get(s).get(date));
            }
            records.put(s,singleWheel);
        }
        return records;
    }

    //需求5.1  传入时间段 查询单个设备白班晚班工作时间
    @RequestMapping(value = "/searchDayShiftByType", produces = "application/json; charset=utf-8")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public DataResult searchDayShiftByType(@RequestBody Map map){
        QueryRo queryRo = new QueryRo();
        DataResult dataResult = new DataResult();
        try {
            int type = Integer.parseInt(map.get("type").toString());
            String startTime = map.get("start").toString();
            String endTime = map.get("end").toString();
//            int type = 1;
//            String startTime = "2019-4-1";
//            String endTime = "2019-4-3";
            queryRo.setStartTime(startTime);
            queryRo.setEndTime(endTime);
            queryRo.setTable_name(TypeConvertTableName.getTable_Name(type));// 设置表名
            List<Grinding_Wheel> wheels = grindService.slectWheelStatus(queryRo);
            HashMap<String,List<Grinding_Wheel>> infoChild = GrindUtil.divByDate(wheels);
            System.out.println("==========="+wheels.size()+"\t"+infoChild.size());
            HashMap<String,Tuple> tupleHashMap = new LinkedHashMap<>();
            for(String date:infoChild.keySet()){
                tupleHashMap.put(date,GrindUtil.getDayShift(wheels));
            }
            int dayValue = 0;
            int nightValue = 0;
            for (String key:tupleHashMap.keySet()){
                dayValue += Integer.parseInt(tupleHashMap.get(key).getName().toString());
                nightValue += Integer.parseInt(tupleHashMap.get(key).getValue().toString());
            }
            ArrayList<Tuple> picinfo = new ArrayList<>();
            picinfo.add(new Tuple("白班",dayValue));
            picinfo.add(new Tuple("晚班",nightValue));
            for(String date:tupleHashMap.keySet()){
                System.out.println(date+"\t"+tupleHashMap.get(date));
            }
            dataResult.setData(picinfo);
            dataResult.setCode(200);
        }catch (Exception e){
            dataResult.setCode(500);
            dataResult.setMsg(e.toString());
        }
        return dataResult;
    }

    // 需求6
    @RequestMapping(value = "/excel/export")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public void exportExcel(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        QueryRo queryRo = new QueryRo();
        int type = Integer.parseInt(request.getParameter("type"));
        int label = Integer.parseInt(request.getParameter("label"));
        queryRo.setStartTime(request.getParameter("start"));
        queryRo.setEndTime(request.getParameter("end"));
        queryRo.setTable_name(TypeConvertTableName.getTable_Name(type));
        List<Grinding_Wheel> list = this.grindService.slectWheelStatus(queryRo);
        List<Tuple> handleList = new ArrayList<>();
        switch (label){
            case 0:
                handleList = GrindUtil.label0(list);
                break;
            case 1:
                handleList = GrindUtil.label1(list);
                break;
            default:
                break;
        }
        HSSFWorkbook wb = ExportExcel.export(handleList);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename="+queryRo.getStartTime()+"-"+queryRo.getEndTime()+".xls");
        OutputStream ouputStream = response.getOutputStream();
        wb.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
    }

    // 分页查询
    @RequestMapping(value = "/searchPage")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public DataResult searchPage(@RequestBody Map map){
        DataResult dataResult = new DataResult();
        List info = new ArrayList();
        Map paramap = new HashMap();
        try {
        String startTime = null;
        String hour = null;
        if(map.get("date")!=null&&map.get("date").toString().trim().length()>0){
            startTime = map.get("date").toString();//
            paramap.put("startTime", TimeFormat.strToDate(startTime).toLocaleString().split(" ")[0]);
            paramap.put("endTime", TimeFormat.getNextDate(startTime).toLocaleString().split(" ")[0]);
        }else{
            paramap.put("startTime",null);
        }
        if (map.get("hour")!=null&&map.get("hour").toString().trim().length()>0){
            hour=map.get("hour").toString();
        }
        int type=Integer.parseInt(map.get("type").toString());
        int page = Integer.parseInt(map.get("page").toString());
        int pageSize = Integer.parseInt(map.get("pageSize").toString());
            paramap.put("hour", hour);
            paramap.put("page", page);
            paramap.put("pageSize", pageSize);
            paramap.put("table_name", TypeConvertTableName.getTable_Name(type));
            List<Grinding_Wheel> wheels = grindService.selectWheelPage(paramap);
            for (Grinding_Wheel wheel : wheels) {
                info.add(wheel);
            }
            dataResult.setCode(200);
            dataResult.setData(info);
        }catch (Exception e){
            dataResult.setCode(500);
            dataResult.setMsg(e.toString());
        }
        return dataResult;
    }
}
