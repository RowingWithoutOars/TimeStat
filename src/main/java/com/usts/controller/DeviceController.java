package com.usts.controller;

import com.usts.model.DataResult;
import com.usts.model.Device;
import com.usts.service.IDeviceService;
import com.usts.utils.MapConvertObject;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("device")
public class DeviceController {

    @Autowired
    private IDeviceService deviceService;

    // 显示所有信息
    @RequestMapping(value = "/listDevice", produces = "application/json; charset=utf-8")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public DataResult listDevice(){
        DataResult dataResult = new DataResult();
        try {
            List<Device> devices = deviceService.listDevice();
            dataResult.setData(devices);
            dataResult.setCode(200);
        }catch (MyBatisSystemException e){
            dataResult.setCode(500);
            dataResult.setData("数据库异常");
        }
        return dataResult;
    }

    // 按设备名查询
    @RequestMapping(value = "/searchDevice", produces = "application/json; charset=utf-8")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public DataResult searchDevice(@RequestBody Map map){
        DataResult dataResult = new DataResult();
        try {
            String device_name = "";
            if (map.get("device_name")!=null){
                device_name = map.get("device_name").toString();
            }
            List<Device> devices = deviceService.listDevice();
            if (devices.size()>0){
                Device device1 = new Device();
                for(Device device:devices){
                    if (device.getDevice_name().equals(device_name)){
                        device1 = device;
                    }
                }
                dataResult.setData(device1);
            }else{
                dataResult.setMsg("没有找到相关设备");
            }
            dataResult.setCode(200);
        }catch (MyBatisSystemException e){
            dataResult.setCode(500);
            dataResult.setData("数据库异常");
        }
        return dataResult;
    }

    // 增加设备信息
    @RequestMapping(value = "/addDevice", produces = "application/json; charset=utf-8")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public DataResult addDevice(@RequestBody Map map){
        DataResult dataResult = new DataResult();
        Device device = MapConvertObject.mapConverTDev(map);
        deviceService.addDevice(device);
        dataResult.setCode(200);
        dataResult.setMsg("增加成功");
        return dataResult;
    }

    // 修改设备信息
    @RequestMapping(value = "/updateDevice", produces = "application/json; charset=utf-8")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public DataResult updateDevice(@RequestBody Map map){
        DataResult dataResult = new DataResult();
        Device device = MapConvertObject.mapConverTDev(map);
        deviceService.updateDevice(device);
        dataResult.setCode(200);
        dataResult.setData("修改成功");
        return dataResult;
    }

    // 删除设备信息
    @RequestMapping(value = "/deleteDevice", produces = "application/json; charset=utf-8")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public DataResult deleteDevice(@RequestBody Map map) {
        DataResult dataResult = new DataResult();
        Device device = MapConvertObject.mapConverTDev(map);
        deviceService.deleteDevice(device);
        dataResult.setCode(200);
        dataResult.setMsg("删除成功");
        return dataResult;
    }
}
