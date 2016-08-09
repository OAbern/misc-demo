package com.zhubajie.oabern.mock.demo.service;

import com.zhubajie.oabern.mock.demo.dal.UserDao;
import com.zhubajie.oabern.mock.demo.entity.User;

/**
 * Created by fengdi on 2016/8/9.
 */
public class UserService {
    public UserDao userDao;

    public boolean addUser(User user) {
        //检查是否有同名用户
        User userTemp = userDao.findUserByUserName(user.getUserName());
        if(userTemp == null) {
            return userDao.addUser(user);
        }else {
            return false;
        }
    }

    public boolean deleteUser(String userId) {
        return userDao.deleteUser(userId);
    }

    public User findUserByUserId(String userId) {
        return userDao.findUserByUserId(userId);
    }

    public boolean updateUser(User user){
        return userDao.updateUser(user);
    }

    public boolean verify(User user) {
        return false;
    }
}
