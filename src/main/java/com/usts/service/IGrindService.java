package com.usts.service;

import com.usts.model.Grinding_Wheel;
import com.usts.model.QueryRo;

import java.util.List;
import java.util.Map;

public interface IGrindService {
    public void addRecord(Grinding_Wheel grinding_wheel);

    // 统计当前时间的单个设备的工作时间
    //这个方法调用时需要考虑截止时间比数据库中的小，此时会返回null，但是在xml中指定了integer，所有要加异常处理
    public int selectWheelWorkHour(QueryRo queryRo);

    // 查询当天的总工作时间
    public int selectDayWorkHour(QueryRo queryRo);

    // 查询当前设备总的工作时间
    public int selectAllWorkHour(QueryRo queryRo);

    // 查询最后一条纪录，需要传表名
    public Grinding_Wheel selectStatusNew(QueryRo queryRo);

    // 查询当天的纪录
    public List<Grinding_Wheel> selectOnDay(QueryRo queryRo);



    // 柱状图时间统计
    public List<Grinding_Wheel> slectWheelStatus(QueryRo queryRo);

    public List<Grinding_Wheel> selectWheelPage(Map map);

    public Integer selectTotal(QueryRo queryRo);
}
