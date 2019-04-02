package com.usts.controller;

import com.usts.model.DataResult;
import com.usts.model.Users;
import com.usts.service.IUserService;
import com.usts.utils.MapConvertObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    // 登录接口
    @RequestMapping(value = "/login", produces = "application/json; charset=utf-8")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public DataResult login(@RequestBody Map map) {
        DataResult dataResult = new DataResult();

        Users users = MapConvertObject.mapConverTUser(map);
        try {
            users = this.userService.selectUser(users);
            if (users!=null){
                dataResult.setData(users);
                dataResult.setCode(200);
            }else{
                dataResult.setCode(500);
                dataResult.setMsg("用户名或者密码错误");
            }
        }catch (Exception e){
            dataResult.setCode(500);
            dataResult.setMsg(e.toString());
        }
        return dataResult;
    }

    // 增加用户
    @RequestMapping(value = "/addUser", produces = "application/json; charset=utf-8")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public DataResult addUser(@RequestBody Map map) {
        DataResult dataResult = new DataResult();
        Users users = MapConvertObject.mapConverTUser(map);
        Users utemp = userService.selectUser(users);
        try {
            if (utemp==null){//判断是否存在，不存在时为空，可以增加
                userService.addUser(users);
                dataResult.setCode(200);
                dataResult.setData(true);
                dataResult.setMsg("增加成功");
            }else{
                dataResult.setCode(500);
                dataResult.setMsg("用户名已经存在");
                dataResult.setData(false);
            }
        }catch (Exception e){
            dataResult.setCode(500);
            dataResult.setMsg(e.toString());
            dataResult.setData(false);
        }
        return dataResult;
    }

    // 修改用户
    @RequestMapping(value = "/updateUser", produces = "application/json; charset=utf-8")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public DataResult updateUser(@RequestBody Map map) {
        DataResult dataResult = new DataResult();
        Users users = MapConvertObject.mapConverTUser(map);
        Users utemp = userService.selectUser(users);
        if (utemp!=null){// 用户存在即可修改,否则不能修改
            userService.updateUser(users);
            dataResult.setCode(200);
            dataResult.setData(true);
            dataResult.setMsg("修改成功");
        }else{
            dataResult.setCode(500);
            dataResult.setData(false);
            dataResult.setMsg("用户不存在");
        }
        return dataResult;
    }

    // 删除用户
    @RequestMapping(value = "/deleteUser", produces = "application/json; charset=utf-8")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public DataResult deleteUser(@RequestBody Map map) {
        DataResult dataResult = new DataResult();
        Users users = MapConvertObject.mapConverTUser(map);
        Users utemp = userService.selectUser(users);
        if (utemp!=null){
            userService.deleteUser(utemp);
            dataResult.setCode(200);
            dataResult.setData(true);
            dataResult.setMsg("删除成功");
        }else{
            dataResult.setCode(500);
            dataResult.setData(false);
            dataResult.setMsg("删除失败");
        }
        return dataResult;
    }
}
