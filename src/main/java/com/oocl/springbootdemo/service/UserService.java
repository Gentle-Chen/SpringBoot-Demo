package com.oocl.springbootdemo.service;

import com.oocl.springbootdemo.model.User;

public interface UserService {

	User insertUser(User user);

	User getUserByName(String name);

	int updateUserByName(String name, User user);

	int deleteUserByName(String name);

}
