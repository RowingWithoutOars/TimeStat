package com.usts.dao;

import com.usts.model.Grinding_Wheel;
import com.usts.model.QueryRo;

import java.util.List;
import java.util.Map;

public interface IGrindDao {
    public void addRecord(Grinding_Wheel grinding_wheel);
    public int selectWheelWorkHour(QueryRo queryRo);
    public List<Grinding_Wheel> slectWheelStatus(QueryRo queryRo);
    // 查询最后一条纪录，需要传表名
    public Grinding_Wheel selectStatusNew(QueryRo queryRo);

    // 查询当天的纪录
    public List<Grinding_Wheel> selectOnDay(QueryRo queryRo);
    // 查询当天的总工作时间
    public int selectDayWorkHour(QueryRo queryRo);

    public List<Grinding_Wheel> selectStatusLastF(QueryRo queryRo);

            // 查询当前设备总的工作时间
    public int selectAllWorkHour(QueryRo queryRo);
    public List<Grinding_Wheel> selectWheelPage(Map map);

    public Integer selectTotal(Map map);
    public List<Grinding_Wheel> selectDayWorkHour_Hour_MuchRecords(QueryRo queryRo);

    public List<Grinding_Wheel> selectAllWorkHour_Hour_MuchRecords(QueryRo queryRo);
}
