package com.oocl.springbootdemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oocl.springbootdemo.dao.UserDao;
import com.oocl.springbootdemo.model.User;
import com.oocl.springbootdemo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public User insertUser(User user) {
		// TODO Auto-generated method stub
		return userDao.insertUser(user);
	}

	@Override
	public User getUserByName(String name) {
		// TODO Auto-generated method stub
		return userDao.getUserByName(name);
	}

	@Override
	public int updateUserByName(String name, User user) {
		// TODO Auto-generated method stub
		return userDao.updateUserByName(name, user);
	}

	@Override
	public int deleteUserByName(String name) {
		// TODO Auto-generated method stub
		return userDao.deleteUserByName(name);
	}

}
