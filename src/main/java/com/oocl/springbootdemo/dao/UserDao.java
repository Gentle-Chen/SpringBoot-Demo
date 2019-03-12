package com.oocl.springbootdemo.dao;

import com.oocl.springbootdemo.model.User;

public interface UserDao {

	User insertUser(User user);

	User getUserByName(String name);

	int updateUserByName(String name, User user);

	int deleteUserByName(String name);

}
