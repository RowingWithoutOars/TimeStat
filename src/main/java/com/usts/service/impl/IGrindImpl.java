package com.usts.service.impl;

import com.usts.dao.IGrindDao;
import com.usts.model.Grinding_Wheel;
import com.usts.model.QueryRo;
import com.usts.service.IGrindService;
import com.usts.utils.GrindUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class IGrindImpl implements IGrindService {
    Map<Integer,Map<Integer,Grinding_Wheel>> cacheAllWorkMap = new LinkedHashMap<>();

    //所有的工作时间

    Map<Integer,Map<Integer,Grinding_Wheel>> cacheDayWorkMap = new LinkedHashMap<>();

    @Resource
    private IGrindDao grindDao;
    @Override
    public void addRecord(Grinding_Wheel grinding_wheel) {
        grindDao.addRecord(grinding_wheel);
    }

    @Override
    public int selectWheelWorkHour(QueryRo queryRo) {
        return grindDao.selectWheelWorkHour(queryRo);
    }

    @Override
    public int selectDayWorkHour(QueryRo queryRo) {
        return grindDao.selectDayWorkHour(queryRo);
    }

    @Override
    public int selectAllWorkHour(QueryRo queryRo) {
        return grindDao.selectAllWorkHour(queryRo);
    }

    @Override
    public Grinding_Wheel selectStatusNew(QueryRo queryRo) {
        return grindDao.selectStatusNew(queryRo);
    }

    @Override
    public List<Grinding_Wheel> selectStatusLastF(QueryRo queryRo) {
        return grindDao.selectStatusLastF(queryRo);
    }

    @Override
    public List<Grinding_Wheel> selectOnDay(QueryRo queryRo) {
        return grindDao.selectOnDay(queryRo);
    }

    @Override
    public List<Grinding_Wheel> slectWheelStatus(QueryRo queryRo) {
        return grindDao.slectWheelStatus(queryRo);
    }

    @Override
    public List<Grinding_Wheel> selectWheelPage(Map map) {
        return grindDao.selectWheelPage(map);
    }

    @Override
    public Integer selectTotal(Map map) {
        return grindDao.selectTotal(map);
    }

    // 获取一天的工作时间
    @Override
    public int selectDayWorkHour_Hour_MuchRecords(QueryRo queryRo) {
        List<Grinding_Wheel> wheels = grindDao.selectDayWorkHour_Hour_MuchRecords(queryRo);
        return GrindUtil.getDayWorking_HourMuchRecords(wheels);
    }

    public Grinding_Wheel selectDayWorkStatus_Hour_MuchRecords(QueryRo queryRo){
        List<Grinding_Wheel> wheels = grindDao.selectDayWorkHour_Hour_MuchRecords(queryRo);
        Grinding_Wheel wheel = new Grinding_Wheel();
        if (wheels.isEmpty()&&wheels.size()>0){
            wheel = wheels.get(wheels.size()-1);
        }
        return wheel;
    }

    // 获取总的工作时间
    @Override
    public int selectAllWorkHour_Hour_MuchRecords(QueryRo queryRo) {
        int allHourWorking = 0;
        List<Grinding_Wheel> selectResult = grindDao.selectAllWorkHour_Hour_MuchRecords(queryRo);
        //按日期分开
        HashMap<String,List<Grinding_Wheel>> divByDate = GrindUtil.divByDate(selectResult);
        for(String key:divByDate.keySet()){
            // 把每天的加起来
            allHourWorking+=GrindUtil.getDayWorking_HourMuchRecords(divByDate.get(key));
        }
        return allHourWorking;
    }

}
