package com.oocl.springbootdemo.service;

import com.oocl.springbootdemo.model.AutoConfigModel;

public class AutoConfigService {

	private int code;
	
	private String msg;

	public void setCode(int code) {
		this.code = code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public AutoConfigModel testAutoConfig() {
		return new AutoConfigModel(code, msg);
	}
	
}
