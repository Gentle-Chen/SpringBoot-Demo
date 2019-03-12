package com.oocl.springbootdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.oocl.springbootdemo.constant.GlobalConstant;
import com.oocl.springbootdemo.model.AutoConfigModel;
import com.oocl.springbootdemo.model.Response;
import com.oocl.springbootdemo.model.User;
import com.oocl.springbootdemo.service.AutoConfigService;
import com.oocl.springbootdemo.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AutoConfigService autoConfigService;
	
	@PostMapping("/user")
	public Response insertUser(@RequestBody User user){
		Response response = new Response();
		User result = userService.insertUser(user);
		response.setStatus(GlobalConstant.SUCCESS);
		response.setResult(result);
		return response; 
	}
	
	@GetMapping("/user/{name}")
	public Response getUserByName(@PathVariable("name") String name) {
		Response response = new Response();
		try{
			User user = userService.getUserByName(name);
			response.setStatus(GlobalConstant.SUCCESS);
			response.setResult(user);
		}catch (Exception e) {
			// TODO: handle exception
			response.setStatus("fail");
			response.setErrorMsg(e.getMessage() + " for select");
		}
		return response;
	}
	
	@PutMapping("/user/{name}")
	public Response updateUserByName(@PathVariable("name") String name, @RequestBody User user) {
		Response response = new Response();
		try {
			int i = userService.updateUserByName(name, user);
			response.setStatus(GlobalConstant.SUCCESS);
			response.setUpdateCount(i);
		}catch (Exception e) {
			response.setStatus("fail");
			response.setErrorMsg(e.getMessage() + " for update");
		}
		return response;
	}
	
	@DeleteMapping("/user/{name}")
	public Response deleteUserByName(@PathVariable("name") String name) {
		Response response = new Response();
		try {
			int i = userService.deleteUserByName(name);
			response.setStatus(GlobalConstant.SUCCESS);
			response.setUpdateCount(i);
		}catch (Exception e) {
			response.setStatus("fail");
			response.setErrorMsg(e.getMessage() + " for delete");
		}
		return response;
	}
	
	@GetMapping("/autoconfig")
	public AutoConfigModel testAutoConfig() {
		return autoConfigService.testAutoConfig();
	}
}
