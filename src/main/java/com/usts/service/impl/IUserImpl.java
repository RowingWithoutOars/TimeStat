package com.usts.service.impl;

import com.usts.dao.IUserDao;
import com.usts.model.Users;
import com.usts.service.IUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IUserImpl implements IUserService {

    @Resource
    private IUserDao userDao;
    @Override
    public List<Users> listUser() {
        return userDao.listUser();
    }

    @Override
    public Users selectUser(Users users) {
        return userDao.selectUser(users);
    }

    @Override
    public void addUser(Users users) {
        userDao.addUser(users);
    }

    @Override
    public void updateUser(Users users) {
        userDao.updateUser(users);
    }

    @Override
    public void deleteUser(Users users) {
        userDao.deleteUser(users);
    }

}
