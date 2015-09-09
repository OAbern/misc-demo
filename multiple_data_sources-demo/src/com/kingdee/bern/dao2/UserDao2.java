package com.kingdee.bern.dao2;

import org.apache.ibatis.annotations.Param;

import com.kingdee.bern.bean.User;

/**
 * 查找用户的Dao
 * @author sola
 *
 */
public interface UserDao2 {
	
	public User findUser(@Param("id")int id);
	
}
