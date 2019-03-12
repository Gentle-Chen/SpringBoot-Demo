package com.oocl.springbootdemo.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oocl.springbootdemo.model.User;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserDaoImplTest {

	@Autowired
	private UserDao userDao;
	
	@Test
	public void insertUserTest() {
		User user = new User("chenge6@oocl.com", "gentle", "male");
		User result = userDao.insertUser(user);
		assertEquals(user, result);
	}
	
	@Test(expected = NullPointerException.class) 
	public void should_throw_null_exception_when_input_wrong_name_for_select() {
		String name = "gentle";
		userDao.getUserByName(name);
	}
	
	@Test
	public void should_return_user_when_input_right_name_for_select() {
		String name = "gentle";
		User user = userDao.getUserByName(name);
		assertEquals(name, user.getName());
	}
	
	@Test(expected = NullPointerException.class)
	public void should_throw_null_exception_when_input_wrong_name_for_update() {
		String name = "gentle";
		User user = new User("chenge@oocl.com", "gentle", "male");
		userDao.updateUserByName(name, user);
	}
	
	@Test
	public void should_return_update_count_when_input_right_name_for_update() {
		String name = "gentle";
		User user = new User("chenge@oocl.com", "gentle", "male");
		int result = userDao.updateUserByName(name, user);
		assertEquals(1, result);
	}
	
	@Test(expected = NullPointerException.class)
	public void should_throw_null_exception_when_input_wrong_name_for_delete() {
		String name = "gentle";
		userDao.deleteUserByName(name);
	}
	
	@Test
	public void should_return_delete_count_when_input_right_name_for_delete() {
		String name = "gentle";
		int result = userDao.deleteUserByName(name);
		assertEquals(1, result);
	}

}
