package com.usts.service.impl;

import com.usts.dao.IGrindDao;
import com.usts.model.Grinding_Wheel;
import com.usts.model.QueryRo;
import com.usts.service.IGrindService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class IGrindImpl implements IGrindService {

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
}
