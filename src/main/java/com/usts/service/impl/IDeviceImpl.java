package com.usts.service.impl;

import com.usts.dao.IDeviceDao;
import com.usts.model.Device;
import com.usts.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IDeviceImpl implements IDeviceService {

    @Autowired
    private IDeviceDao deviceDao;


    @Override
    public List<Device> listDevice() {
        return deviceDao.listDevice();
    }

    @Override
    public Device selectDevice(Device device) {
        return deviceDao.selectDevice(device);
    }

    @Override
    public void addDevice(Device device) {
        deviceDao.addDevice(device);
    }

    @Override
    public void updateDevice(Device device) {
        deviceDao.updateDevice(device);
    }

    @Override
    public void deleteDevice(Device device) {
        deviceDao.deleteDevice(device);
    }
}
