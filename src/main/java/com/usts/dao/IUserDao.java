package com.usts.dao;

import com.usts.model.Users;

import java.util.List;

public interface IUserDao {
    // 列出所有用户
    public List<Users> listUser();
    // 查找用户
    public Users selectUser(Users users);
    // 新增用户
    public void addUser(Users users);
    // 修改用户
    public void updateUser(Users users);
    //删除用户
    public void deleteUser(Users users);
}
