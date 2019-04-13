//package com.usts;
//
//import com.usts.dao.IGrindDao;
//import com.usts.dao.IUserDao;
//import com.usts.model.*;
//import com.usts.service.IGrindService;
//import com.usts.utils.GrindUtil;
//import com.usts.utils.TimeFormat;
//import com.usts.utils.TypeConvertTableName;
//import org.junit.runner.RunWith;
//import org.mybatis.spring.MyBatisSystemException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.sql.Time;
//import java.text.ParseException;
//import java.text.ParsePosition;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"classpath:spring-mybatis.xml"})
//public class Test {
//    @Autowired
//    private IUserDao userDao;
//
//    @org.junit.Test
//    public void testUserFun(){
////        List<Users> users = userDao.listUser();
////        System.out.println(users.size());
//        Users users1 = new Users("Test3","Test3");
//        userDao.addUser(users1);
////        Users u1 = userDao.selectUser(users1);
////        System.out.println(u1);
////        userDao.deleteUser(users1);
//////        u1.setMenu("1111");
//////        u1.setPassword("2222");
//////        userDao.updateUser(u1);
//
//    }
//
//    @Autowired
//    private IGrindDao grindDao;
//    @org.junit.Test
//    public void testGrindFun() throws ParseException {
////        add();
////        xuqiu2();
////        xuqiu3();
////        Histogram();
//        xuqiu5();
////        searchPage();
////        xuqiu6();
//    }
//
//    public void xuqiu6(){
//        QueryRo queryRo = new QueryRo();
//        int label = 0;
//        queryRo.setStartTime("2025-3-31");
//        queryRo.setEndTime("2025-4-2");
//        queryRo.setTable_name(TypeConvertTableName.getTable_Name(1));
//        List<Grinding_Wheel> wheels = grindDao.selectTimeWorkHour(queryRo);//查询时间段内的数据
//        label1(wheels);
//
//    }
//    public void label0(List<Grinding_Wheel> wheels){
//        List<Tuple> info = new ArrayList<>();
//        for (Grinding_Wheel wheel:wheels){
//            String time = wheel.getDtime().toLocaleString().split(" ")[0];
//            Tuple tuple = new Tuple(time+"号"+GrindUtil.hourConverTime(wheel.getDhour()),GrindUtil.converToMin(wheel.getDworkinghour()));
//            info.add(tuple);
//        }
//    }
//    public void label1(List<Grinding_Wheel> wheels){
//        //        for(Grinding_Wheel wheel:wheels){
////            System.out.println(wheel);
////        }
//        //按照时间将所有纪录分开，方便统计白班和晚班
//        HashMap<String,List<Grinding_Wheel>> info = GrindUtil.divByDate(wheels);
//        List<Tuple> dayShiftNight = new ArrayList<>();
////        HashMap<String,Tuple> dayShiftNight = new HashMap<>();
//        for(String key:info.keySet()){
////            dayShiftNight.put(key,GrindUtil.getDayShift(info.get(key)));
//            Tuple tmp = GrindUtil.getDayShift(info.get(key));
//            dayShiftNight.add(new Tuple(key+"白班",tmp.getName()));
//            dayShiftNight.add(new Tuple(key+"晚班",tmp.getValue()));
//        }
//        for (Tuple str:dayShiftNight){
//            System.out.println(str.getName()+"\t"+GrindUtil.converToHour(Integer.parseInt(str.getValue().toString())));
//        }
//    }
//
//    public void searchPage(){
//        Map paramap = new HashMap();
//        paramap.put("date","2025-4-01");
//        paramap.put("startTime",TimeFormat.strToDate("2025-04-01").toLocaleString().split(" ")[0]);
//        paramap.put("endTime",TimeFormat.getNextDateStr("2025-04-05"));
//        paramap.put("hour",18);
//        int type=1;
//        paramap.put("page",1);
//        paramap.put("pageSize",10);
//        paramap.put("table_name",TypeConvertTableName.getTable_Name(type));
//        List<Grinding_Wheel> wheels = grindDao.selectWheelPage(paramap);
//        System.out.println("===="+wheels.size());
//        List<Grinding_Wheel> wheels1 = GrindUtil.pageSearch(wheels,8,16);
//        for (Grinding_Wheel wheel:wheels1){
//            System.out.println("==============="+wheel);
//        }
//    }
//
//    public void xuqiu5(){
//        QueryRo queryRo = new QueryRo();
//        String startTime = "2019-04-11T16:00:00.000Z";
//        String endTime = "2019-04-11T16:00:00.000Z";
//        queryRo.setTable_name(TypeConvertTableName.getTable_Name(1));// 设置表名
//        queryRo.setStartTime(startTime);
//        queryRo.setEndTime(endTime);
//        List<Grinding_Wheel> wheels = grindDao.selectTimeWorkHour(queryRo);
//        HashMap<String,List<Grinding_Wheel>> infoChild = GrindUtil.divByDate(wheels);
//        HashMap<String,Tuple> tupleHashMap = new LinkedHashMap<>();
//        for(String date:infoChild.keySet()){
//            tupleHashMap.put(date,GrindUtil.getDayShift(infoChild.get(date)));
//        }
//        for(String date:tupleHashMap.keySet()){
//            System.out.println(date+"\t"+tupleHashMap.get(date));
//        }
//        int dayValue = 0;
//        int nightValue = 0;
//        for (String key:tupleHashMap.keySet()){
//            dayValue += Integer.parseInt(tupleHashMap.get(key).getName().toString());
//            nightValue += Integer.parseInt(tupleHashMap.get(key).getValue().toString());
//        }
//        System.out.println(dayValue+"\t"+nightValue);
//
//    }
//    @Autowired
//    private IGrindService grindService;
//
//    public void xuqiu3(){
//        Map<String,WheelStatus> info = new HashMap();
//        QueryRo queryRo = new QueryRo();
//        DataResult dataResult = new DataResult();
//        queryRo.setStartTime("2019-4-1");
//        queryRo.setEndTime("2019-4-8");
//        try {
//            ArrayList<String> table_name = TypeConvertTableName.getAllTableName();
//            HashMap<String, Grinding_Wheel> status = new LinkedHashMap<>();
//            HashMap<String, Integer> allTime = new LinkedHashMap<>();
//            HashMap<String, Integer> dayTime = new LinkedHashMap<>();
//            Map<String,List<Grinding_Wheel>> status_new = new LinkedHashMap<>();
//
////            for (String name : table_name) {
////                queryRo.setTable_name(name);// 设置表名
////                status_new.put(name,grindService.selectStatusLastF(queryRo));
////                Grinding_Wheel status_New = grindService.selectDayWorkStatus_Hour_MuchRecords(queryRo);
////                status.put(name, status_New);
////                allTime.put(name, grindService.selectAllWorkHour_Hour_MuchRecords(queryRo));
////                dayTime.put(name, grindService.selectDayWorkHour_Hour_MuchRecords(queryRo));
////            }
////            for (String key : status.keySet()) {//输出八个设备的当前工作状态
////                WheelStatus wheelStatus = new WheelStatus();
////                wheelStatus.setStatus(GrindUtil.isWorkingOff(status_new.get(key)));// 设备状态
////                wheelStatus.setAll_work(allTime.get(key));// 设备
////                wheelStatus.setCurrent_day_work(dayTime.get(key));
////                wheelStatus.setCurrent_hour_work(status.get(key).getDworkinghour());
////                info.put(key, wheelStatus);
////            }
//            dataResult.setData(info);
//            dataResult.setCode(200);
//        }catch (NullPointerException e){
//            dataResult.setCode(500);
//            dataResult.setMsg("没有查找相关的数据");
//            dataResult.setError(e.toString());
//        }catch (MyBatisSystemException e){
//            dataResult.setCode(500);
//            dataResult.setMsg("数据库连接异常");
//        }
//    }
//
//    public void xuqiu2(){
//        QueryRo queryRo = new QueryRo();
//        int type = 1;
//        queryRo.setTable_name(TypeConvertTableName.getTable_Name(type));
//        String startTime = "2019-3-31 00:00:00";
//        String endTime = "2019-4-1 00:00:00";
//        queryRo.setStartTime(startTime);
//        queryRo.setEndTime(endTime);
//        List<Grinding_Wheel> wheels = grindDao.selectTimeWorkHour(queryRo);
//        Grinding_Wheel wheel = GrindUtil.getHourWorkig(wheels,17);
//        System.out.println(wheel.toString());
//    }
//
//    public void Histogram(){
//        QueryRo queryRo = new QueryRo();
//        int type = 1;
//        queryRo.setTable_name(TypeConvertTableName.getTable_Name(type));
//        String startTime = "2019-4-1";
//        String endTime = "2019-4-5";
//        queryRo.setStartTime(startTime);
//        queryRo.setEndTime(endTime);
//        List<Grinding_Wheel> lis = grindDao.selectTimeWorkHour(queryRo);
//        HashMap<String,Integer> info = GrindUtil.getHistogramInfo(lis);
//        for(String s : info.keySet()){
//            System.out.println("dtime: "+s+"\t dworkighour："+info.get(s));
//        }
//        System.out.println("========================================");
//        info = GrindUtil.getHistogramSingleInfo(lis);
//        for (String s:info.keySet()){
//            System.out.println(s+"\t"+info.get(s));
//        }
//    }
//
//    public void add(){
//        Grinding_Wheel grinding_wheel = new Grinding_Wheel();
//        Date date = new Date();// 获取现在时间
//        for (int i =0; i<10000; i++){
//            grinding_wheel.setDtime(date);
//            int hour = date.getHours();// 获取小时数
//            grinding_wheel.setDhour(hour);
//            grinding_wheel.setDnum(1);
//            grinding_wheel.setDworkinghour((int) (Math.random()*3600));
//            System.out.println("================="+date);
//            System.out.println(grinding_wheel);
//            grindDao.addRecord(grinding_wheel);
//            date = strToDateLong(getPreTime(getStringDateShort(date),"1"));
//        }
//    }
//
//    public static Date getNowDateShort(Date currentTime) {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateString = formatter.format(currentTime);
//        ParsePosition pos = new ParsePosition(8);
//        Date currentTime_2 = formatter.parse(dateString, pos);
//        return currentTime_2;
//    }
//    public static String getStringDateShort(Date currentTime) {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateString = formatter.format(currentTime);
//        return dateString;
//    }
//
//    /**
//     * 时间前推或后推分钟,其中JJ表示分钟.
//     */
//    public static String getPreTime(String sj1, String jj) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String mydate1 = "";
//        try {
//            Date date1 = format.parse(sj1);
//            long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
//            date1.setTime(Time * 1000);
//            mydate1 = format.format(date1);
//        } catch (Exception e) {
//        }
//        return mydate1;
//    }
//    /**
//     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
//     *
//     * @param strDate
//     * @return
//     */
//    public static Date strToDateLong(String strDate) {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        ParsePosition pos = new ParsePosition(0);
//        Date strtodate = formatter.parse(strDate, pos);
//        return strtodate;
//    }
//}
