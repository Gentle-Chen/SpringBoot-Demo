package com.oocl.springbootdemo.dao;

import java.util.List;

import com.oocl.springbootdemo.model.User;

public interface UserDao {

	User insertUser(User user);

	List<User> getAllUser();

	User getUserByName(String name);

	int updateUserByName(String name, User user);

	int deleteUserByName(String name);

}
