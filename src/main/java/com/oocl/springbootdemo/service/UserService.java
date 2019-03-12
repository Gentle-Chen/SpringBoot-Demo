package com.oocl.springbootdemo.service;

import java.util.List;

import com.oocl.springbootdemo.model.User;

public interface UserService {

	User insertUser(User user);

	User getUserByName(String name);
	
	List<User> getAllUser();

	int updateUserByName(String name, User user);

	int deleteUserByName(String name);

}
