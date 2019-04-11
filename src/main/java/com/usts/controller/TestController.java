package com.usts.controller;

import com.usts.model.DataResult;
import com.usts.model.QueryRo;
import com.usts.service.IGrindService;
import com.usts.utils.TypeConvertTableName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/helloworld", produces = "application/json; charset=utf-8")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public Map test() {
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("test","HelloWorld Version1.6");
        return  returnMap;
    }

    @Autowired
    IGrindService grindService;

    @RequestMapping(value = "/testSqlConnectStatus", produces = "application/json; charset=utf-8")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public DataResult testSqlConnectStatus(){
        DataResult dataResult = new DataResult();
        Map map = new HashMap();
        map.put("table_name",TypeConvertTableName.getTable_Name(1));
        try {
            int i = grindService.selectTotal(map);
            dataResult.setCode(200);
            dataResult.setData(i);
        }catch (Exception e){
            dataResult.setCode(500);
        }
        return dataResult;
    }
}
