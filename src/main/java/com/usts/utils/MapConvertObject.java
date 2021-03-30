package com.usts.utils;

import com.usts.model.*;

import java.util.HashMap;
import java.util.List;
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

    public static DeviceManage mapConverTDM(Map map){
        DeviceManage deviceManage = new DeviceManage();
        if(map.get("deviceid")!=null){
            deviceManage.setDeviceid(Integer.parseInt(map.get("deviceid").toString()));
        }
        if(map.get("dname")!=null){
            deviceManage.setDname(map.get("dname").toString());
        }
        return deviceManage;
    }

    public static PassageWay mapConverTPW(Map map){
        PassageWay passageWay = new PassageWay();
        if(map.get("deviceid")!=null){
            passageWay.setDeviceid(Integer.parseInt(map.get("deviceid").toString()));
        }
        if(map.get("dnumid")!=null){
            passageWay.setDnumid(Integer.parseInt(map.get("dnumid").toString()));
        }
        if(map.get("dname")!=null){
            passageWay.setDname(map.get("dname").toString());
        }
        return passageWay;
    }

    public static Labels mapConverTLabel(Map map){
        Labels labels = new Labels();
        if(map.get("labelid")!=null){
            labels.setLabelid(Integer.parseInt(map.get("labelid").toString()));
        }
        if (map.get("name")!=null){
            labels.setName(map.get("name").toString());
        }
        return labels;
    }

    public static Relations mapConverTRelation(Map map){
        Relations relations = new Relations();
        if(map.get("reid")!=null){
            relations.setReid(Integer.parseInt(map.get("reid").toString()));
        }
        if(map.get("deviceid")!=null){
            relations.setDeviceid(Integer.parseInt(map.get("deviceid").toString()));
        }
        if(map.get("dnumid")!=null){
            relations.setDnumid(Integer.parseInt(map.get("dnumid").toString()));
        }
        if(map.get("labelid")!=null){
            relations.setLabelid(Integer.parseInt(map.get("labelid").toString()));
        }
        return relations;
    }


}
