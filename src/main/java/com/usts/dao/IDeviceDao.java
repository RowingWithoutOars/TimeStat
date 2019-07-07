package com.usts.dao;

import com.usts.model.Device;

import java.util.List;

public interface IDeviceDao {
    // 列出所有的设备信息
    public List<Device> listDevice();
    // 查找设备信息
    public Device selectDevice(Device device);
    // 新增设备信息
    public void addDevice(Device device);
    // 修改设备信息
    public void updateDevice(Device device);
    // 删除设备信息
    public void deleteDevice(Device device);
}
