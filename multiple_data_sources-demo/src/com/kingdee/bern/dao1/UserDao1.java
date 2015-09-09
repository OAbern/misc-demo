package com.kingdee.bern.dao1;

import org.apache.ibatis.annotations.Param;

import com.kingdee.bern.bean.User;

/**
 * 查找用户的Dao
 * @author sola
 *
 */
public interface UserDao1 {
	
	public User findUser(@Param("id")int id);
	
}
