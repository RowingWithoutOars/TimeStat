package com.usts.controller;

import com.usts.model.*;
import com.usts.service.IGrindService;
import com.usts.utils.ExportExcel;
import com.usts.utils.GrindUtil;
import com.usts.utils.TimeFormat;
import com.usts.utils.TypeConvertTableName;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.mybatis.spring.MyBatisSystemException;
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
    @RequestMapping(value = "/search_WorkingHour", produces = "application/json; charset=utf-8")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public DataResult searchWorkingHour(@RequestBody Map map) {
        QueryRo queryRo = new QueryRo();
        DataResult dataResult = new DataResult();
        try {
            int type = Integer.parseInt(map.get("type").toString());// 表
            int hour = Integer.parseInt(map.get("hour").toString());// 小时
            String startTime = map.get("date").toString();// 日期
            queryRo.setTable_name(TypeConvertTableName.getTable_Name(type));//设置表名
            queryRo.setStartTime(startTime);
            queryRo.setEndTime(startTime);//设置为一天的
            List<Grinding_Wheel> wheels = grindService.selectTimeWorkHour(queryRo);
            dataResult.setData(GrindUtil.getHourWorkig_HourMuchRecords(wheels, hour));
            dataResult.setCode(200);
        } catch (NullPointerException e) {
            dataResult.setCode(500);
            dataResult.setMsg("没有查找相关的数据");
        } catch (MyBatisSystemException e) {
            dataResult.setCode(500);
            dataResult.setMsg("数据库连接异常");
        }
        return dataResult;
    }

    // 对应需求3
    @RequestMapping(value = "/search_WorkingStatus", produces = "application/json; charset=utf-8")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public DataResult searchWorkingStatus(@RequestBody Map map) {
        Map<String, WheelStatus> info = new HashMap();
        QueryRo queryRo = new QueryRo();
        DataResult dataResult = new DataResult();
        try {
            if (map.get("start") != null && map.get("end") != null) {
                queryRo.setStartTime(map.get("start").toString());
                queryRo.setEndTime(map.get("end").toString());
            } else {
                dataResult.setCode(500);
                dataResult.setMsg("请输入正确的时间段");
                return dataResult;
            }
            ArrayList<String> table_name = TypeConvertTableName.getAllTableName();
            HashMap<String, Grinding_Wheel> status = new LinkedHashMap<>();
            HashMap<String, Integer> allTime = new LinkedHashMap<>();
            HashMap<String, Integer> dayTime = new LinkedHashMap<>();

            for (String name : table_name) {
                queryRo.setTable_name(name);// 设置表名
                // 获取最后三条信息
//                status_new.put(name, grindService.selectStatusLastF(queryRo));
                // 获取最新的纪录
                Grinding_Wheel status_New = grindService.selectStatusNew(queryRo);
                System.out.println(name+"======="+status_New);
                status.put(name, status_New);
                // 获取时间段内总的工作时间
                allTime.put(name, grindService.selectAllWorkHour_Hour_MuchRecords(queryRo));
                // 获取当天的工作时间
                dayTime.put(name, grindService.selectDayWorkHour_Hour_MuchRecords(queryRo));
            }
            for (String key : table_name) {//输出八个设备的当前工作状态
                WheelStatus wheelStatus = new WheelStatus();
//                wheelStatus.setStatus(GrindUtil.isWorkingOff(status_new.get(key)));// 设备状态
                wheelStatus.setStatus(status.get(key).getDstating());
                wheelStatus.setAll_work(allTime.get(key));// 设备
                wheelStatus.setCurrent_day_work(dayTime.get(key));
                System.out.println(key+"======="+status.get(key));
                wheelStatus.setCurrent_hour_work(status.get(key).getDworkinghour());
                info.put(key, wheelStatus);
            }
            dataResult.setData(info);
            dataResult.setCode(200);
        } catch (NullPointerException e) {
            dataResult.setCode(500);
            dataResult.setMsg("没有找到相关数据");
            dataResult.setError(e.toString());
        } catch (MyBatisSystemException e) {
            dataResult.setCode(500);
            dataResult.setMsg("数据库连接异常");
        }
        return dataResult;
    }


    //需求4.1 需要传入时间段 和表名 查询单个的设备工作时间
    @RequestMapping(value = "/search_DayWorkTime", produces = "application/json; charset=utf-8")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
//    @RequestBody Map map
    public DataResult serachDayWorkTime(@RequestBody Map map) {
        DataResult dataResult = new DataResult();
        QueryRo queryRo = new QueryRo();
        try {
            int type = Integer.parseInt(map.get("type").toString());// 表
            String startTime = map.get("start").toString();// 开始时间
            String endTime = map.get("end").toString(); // 结束时间
            queryRo.setTable_name(TypeConvertTableName.getTable_Name(type));
            queryRo.setStartTime(startTime);
            queryRo.setEndTime(endTime);
            List<Grinding_Wheel> lis = grindService.selectTimeWorkHour(queryRo);// 符合时间段的所有数据库数据
            HashMap<String, Integer> info = GrindUtil.getHistogramInfo_HourMuchRecords(lis);
            LinkedPicInfo picInfo = new LinkedPicInfo();
            for (String s : info.keySet()) {
                picInfo.getX().add(s);
                picInfo.getY().add(info.get(s));
            }
            dataResult.setData(picInfo);
            dataResult.setCode(200);
        } catch (NullPointerException e) {
            dataResult.setCode(500);
            dataResult.setMsg("没有查找相关的数据");
            dataResult.setError(e.toString());
        } catch (MyBatisSystemException e) {
            dataResult.setCode(500);
            dataResult.setMsg("数据库连接异常");
            e.toString();
        }
        return dataResult;
    }

    //需求4.2 需要传入时间段 和表名 查询单个的设备一天自然小时的工作时间
    @RequestMapping(value = "/search_HourWork", produces = "application/json; charset=utf-8")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public DataResult serachHourWork(@RequestBody Map map) {
        DataResult dataResult = new DataResult();
        QueryRo queryRo = new QueryRo();
        try {
            int type = Integer.parseInt(map.get("type").toString());// 表
            String startTime = map.get("start").toString();// 开始时间
            String endTime = startTime;
            queryRo.setTable_name(TypeConvertTableName.getTable_Name(type));
            queryRo.setStartTime(startTime);
            queryRo.setEndTime(endTime);
            List<Grinding_Wheel> lis = grindService.selectTimeWorkHour(queryRo);// 符合时间段的所有数据库数据
            lis = GrindUtil.filterDayWorkingHour(lis);
            LinkedPicInfo picInfo = new LinkedPicInfo();
            for (Grinding_Wheel wheel : lis) {
                picInfo.getX().add(wheel.getDhour());
                picInfo.getY().add(wheel.getDworkinghour());
            }
            dataResult.setData(picInfo);
            dataResult.setCode(200);
        } catch (NullPointerException e) {
            dataResult.setCode(500);
            dataResult.setMsg("没有查找相关的数据");
            dataResult.setError(e.toString());
        } catch (MyBatisSystemException e) {
            dataResult.setCode(500);
            dataResult.setMsg("数据库连接异常");
        }
        return dataResult;
    }

    //需求5.1  传入时间段 查询单个设备白班晚班工作时间
    @RequestMapping(value = "/search_DayShiftByType", produces = "application/json; charset=utf-8")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public DataResult searchDayShiftByType(@RequestBody Map map) {
        QueryRo queryRo = new QueryRo();
        DataResult dataResult = new DataResult();
        ArrayList<Tuple> picinfo = new ArrayList<>();
        try {
            int type = Integer.parseInt(map.get("type").toString());
            String startTime = map.get("start").toString();
            String endTime = map.get("end").toString();
            queryRo.setStartTime(startTime,"08:00:00");
            queryRo.setEndTime(endTime,"08:00:00");
            queryRo.setTable_name(TypeConvertTableName.getTable_Name(type));// 设置表名

            List<Grinding_Wheel> wheels = grindService.selectTimeWorkHour(queryRo);
            HashMap<String, List<Grinding_Wheel>> infoChild = GrindUtil.divByDateAndEn(wheels);
            HashMap<String, Tuple> tupleHashMap = new LinkedHashMap<>();
            for (String date : infoChild.keySet()) {
                tupleHashMap.put(date, GrindUtil.getDayShift_HourMuchRecords(infoChild.get(date)));
            }
            int dayValue = 0;
            int nightValue = 0;
            for (String key : tupleHashMap.keySet()) {
                dayValue += Integer.parseInt(tupleHashMap.get(key).getName().toString());
                nightValue += Integer.parseInt(tupleHashMap.get(key).getValue().toString());
            }
            picinfo.add(new Tuple("白班", dayValue));
            picinfo.add(new Tuple("晚班", nightValue));
            for (String date : tupleHashMap.keySet()) {
                System.out.println(date + "\t" + tupleHashMap.get(date));
            }
            dataResult.setData(picinfo);
            dataResult.setCode(200);
        } catch (NullPointerException e) {
            dataResult.setCode(500);
            dataResult.setMsg("没有查找相关的数据");
        } catch (MyBatisSystemException e) {
            dataResult.setCode(500);
            dataResult.setMsg("数据库连接异常");
        }
        return dataResult;
    }

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
        List<Grinding_Wheel> list = this.grindService.selectTimeWorkHour(queryRo);
        List<Tuple> handleList = new ArrayList<>();
        switch (label) {
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
        response.setHeader("Content-disposition", "attachment;filename=" + queryRo.getStartTime() + "-" + queryRo.getEndTime() + ".xls");
        OutputStream ouputStream = response.getOutputStream();
        wb.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
    }

    // 分页查询
    @RequestMapping(value = "/search_Page")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public DataResult searchPage(@RequestBody Map map) {
        DataResult dataResult = new DataResult();
        List info = new ArrayList();
        Map paramap = new HashMap();
        QueryRo queryRo = new QueryRo();
        try {
            String startTime = null;
            String hour = null;
            if (map.get("date") != null && map.get("date").toString().trim().length() > 0) {
                startTime = map.get("date").toString();//
                paramap.put("startTime", startTime);
                paramap.put("endTime", TimeFormat.getNextDateStr(startTime));
            } else {
                paramap.put("startTime", null);
            }
            if (map.get("hour") != null && map.get("hour").toString().trim().length() > 0) {
                hour = map.get("hour").toString();
            }
            int type = 1;
            int page = 0;
            int pageSize = 0;
            if (map.get("type") != null) {
                type = Integer.parseInt(map.get("type").toString());
            }
            if (map.get("page") != null) {
                page = Integer.parseInt(map.get("page").toString());
            }
            if (map.get("pageSize") != null) {
                pageSize = Integer.parseInt(map.get("pageSize").toString());
            }
            paramap.put("hour", hour);
            paramap.put("page", page);
            paramap.put("pageSize", pageSize);
            paramap.put("table_name", TypeConvertTableName.getTable_Name(type));
            queryRo.setTable_name(TypeConvertTableName.getTable_Name(type));
            List<Grinding_Wheel> wheels = grindService.selectWheelPage(paramap);
            //倒置
            wheels = GrindUtil.reverse(wheels);
            List<Grinding_Wheel> wheels1 = GrindUtil.pageSearch(wheels, page, pageSize);
//            for (Grinding_Wheel wheel:wheels1){
//                System.out.println("==============="+wheel.getDtime());
//            }
            for (Grinding_Wheel wheel : wheels1) {
                info.add(wheel);
            }
            int total = wheels.size();
            Map infoMap = new HashMap();
            infoMap.put("list", info);
            infoMap.put("total", total);
            dataResult.setCode(200);
            dataResult.setData(infoMap);
        } catch (NullPointerException e) {
            dataResult.setCode(500);
            dataResult.setMsg("没有查找相关的数据");
        } catch (MyBatisSystemException e) {
            dataResult.setCode(500);
            dataResult.setMsg("数据库连接异常");
        }
        return dataResult;
    }

    @RequestMapping(value = "/search_TimeAllWorkHour")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public DataResult search_TimeAllWorkHour(@RequestBody Map map) {
        DataResult dataResult = new DataResult();
        try {
            QueryRo queryRo = new QueryRo();
            if (map.get("start") != null && map.get("end") != null) {
                queryRo.setStartTime(map.get("start").toString());
                queryRo.setEndTime(map.get("end").toString());
            }
            ArrayList<String> table_names = TypeConvertTableName.getAllTableName();
            LinkedPicInfo linkedPicInfo = new LinkedPicInfo();
            for (int i = 1; i < table_names.size()+1; i++) {
                queryRo.setTable_name(TypeConvertTableName.getTable_Name(i));
                linkedPicInfo.getX().add(i);
                linkedPicInfo.getY().add(grindService.selectAllWorkHour_Hour_MuchRecords(queryRo));
            }
            dataResult.setCode(200);
            dataResult.setData(linkedPicInfo);
        } catch (NullPointerException e) {
            dataResult.setCode(500);
            dataResult.setMsg("没有查找相关的数据");
        } catch (MyBatisSystemException e) {
            dataResult.setCode(500);
            dataResult.setMsg("数据库连接异常");
        }
        return dataResult;
    }

    @RequestMapping(value = "/search_TimeSingleWorkHour")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public DataResult search_TimeSingleWorkHour(@RequestBody Map map) {
        DataResult dataResult = new DataResult();
        try {
            QueryRo queryRo = new QueryRo();
            int type =0;
            if (map.get("start") != null
                    && map.get("end") != null
                        && map.get("type")!=null) {
                queryRo.setStartTime(map.get("start").toString());
                queryRo.setEndTime(map.get("end").toString());
                type = Integer.parseInt(map.get("type").toString());
                if (type<1||type>8){
                    dataResult.setMsg("表输入不正确"+":"+type);
                    dataResult.setCode(500);
                    return dataResult;
                }
            }
            queryRo.setTable_name(TypeConvertTableName.getTable_Name(type));
            List<Grinding_Wheel> wheels = grindService.selectTimeWorkHour(queryRo);
            HashMap<String, List<Grinding_Wheel>> infoChild = GrindUtil.divByDate(wheels);
            LinkedPicInfo picInfo = new LinkedPicInfo();
            for (String key:infoChild.keySet()){
//                System.out.println("============="+key);
//                System.out.println(infoChild.get(key).size());
             picInfo.getX().add(key);
             picInfo.getY().add(GrindUtil.getDayWorking_HourMuchRecords(infoChild.get(key)));
            }
            dataResult.setCode(200);
            dataResult.setData(picInfo);
        } catch (NullPointerException e) {
            dataResult.setCode(500);
            dataResult.setMsg("没有查找相关的数据");
        } catch (MyBatisSystemException e) {
            dataResult.setCode(500);
            dataResult.setMsg("数据库连接异常");
        }
        return dataResult;
    }
}
