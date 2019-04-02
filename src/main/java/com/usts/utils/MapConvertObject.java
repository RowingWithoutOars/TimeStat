package com.usts.utils;

import com.usts.model.QueryRo;
import com.usts.model.Users;

import java.util.Map;

public class MapConvertObject {
    public static Users mapConverTUser(Map map){
        Users users = new Users();
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
}
