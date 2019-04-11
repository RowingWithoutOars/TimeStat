package com.usts.utils;

import com.usts.model.Device;
import com.usts.model.QueryRo;
import com.usts.model.Users;

import java.util.Map;

public class MapConvertObject {
    public static Users mapConverTUser(Map map){
        Users users = new Users();
        if(map.get("userid")!=null){
            users.setUserid(Integer.parseInt(map.get("userid").toString()));
        }
        if (map.get("username")!=null){
            users.setUsername(map.get("username").toString());
        }
        if (map.get("password")!=null){
            users.setPassword(map.get("password").toString());
        }
        if (map.get("menu")!=null){
            users.setMenu(map.get("menu").toString());
        }
        if (map.get("role")!=null){
            users.setRole(Integer.parseInt(map.get("role").toString()));
        }
        return users;
    }

    // 将map转化为对象信息
    public static Device mapConverTDev(Map map){
        Device device = new Device();
        if (map.get("deviceid")!=null){
            device.setDeviceid(Integer.parseInt(map.get("deviceid").toString()));
        }
        if (map.get("device_name")!=null){
            device.setDevice_name(map.get("device_name").toString());
        }
        if (map.get("device_table")!=null){
            device.setDevice_table(map.get("device_table").toString());
        }
        if (map.get("device_num")!=null){
            device.setDevice_num(Integer.parseInt(map.get("device_num").toString()));
        }
        if (map.get("device_status")!=null){
            device.setDevice_status(Integer.parseInt(map.get("device_status").toString()));
        }
        return device;
    }
}
