package com.oocl.springbootdemo.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auto.config")
public class AutoConfigModel {
	
	private int code;
	
	private String msg;
	
	public AutoConfigModel() {
		super();
	}

	public AutoConfigModel(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

}
