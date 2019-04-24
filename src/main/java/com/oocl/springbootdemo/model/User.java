package com.oocl.springbootdemo.model;

import java.io.IOException;
import org.codehaus.jackson.map.ObjectMapper;

public class User {
	
	private String account;
	
	private String name;
	
	private String gender;

	
	public User() {
		super();
	}

	public User(String account, String name, String gender) {
		super();
		this.account = account;
		this.name = name;
		this.gender = gender;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "User [account=" + account + ", name=" + name + ", gender=" + gender + "]";
	}
	
	public static String getJson(User user) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static User getUser(String json) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			User user = objectMapper.readValue(json, User.class);
			return user;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
