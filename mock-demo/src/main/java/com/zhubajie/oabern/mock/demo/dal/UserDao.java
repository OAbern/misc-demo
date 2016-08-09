package com.zhubajie.oabern.mock.demo.dal;

import com.zhubajie.oabern.mock.demo.entity.User;

/**
 * Created by fengdi on 2016/8/9.
 */
public interface UserDao {

    public boolean addUser(User user);

    public boolean deleteUser(String userId);

    public User findUserByUserId(String userId);

    public User findUserByUserName(String username);

    public boolean updateUser(User user);

}
