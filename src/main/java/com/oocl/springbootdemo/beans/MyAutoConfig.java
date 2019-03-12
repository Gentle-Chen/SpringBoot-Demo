package com.oocl.springbootdemo.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oocl.springbootdemo.model.AutoConfigModel;
import com.oocl.springbootdemo.service.AutoConfigService;

@Configuration
@EnableConfigurationProperties(AutoConfigModel.class)
@ConditionalOnClass(MyAutoConfig.class)
public class MyAutoConfig {
	
	@Autowired
	AutoConfigModel autoConfigModel;
	
	@Bean
	public AutoConfigService service() {
		AutoConfigService service = new AutoConfigService();
		service.setCode(autoConfigModel.getCode());
		service.setMsg(autoConfigModel.getMsg());
		return service;
	}

}
