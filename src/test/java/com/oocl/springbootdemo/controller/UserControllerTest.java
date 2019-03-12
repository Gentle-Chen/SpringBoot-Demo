package com.oocl.springbootdemo.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.oocl.springbootdemo.model.User;


@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerTest {

	private MockMvc mvc;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Before
	public void setup() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void should_return_error_msg_when_input_wrong_name_for_select() throws Exception {
		String name = "gentle";
		MvcResult mvcResult = mvc.perform(get("/user/"+name+"")).andExpect(status().isOk()).andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		System.out.println(result);
	}
	
	@Test
	public void should_return_user_when_input_right_name_for_select() throws Exception {
		String name = "gentle";
		MvcResult mvcResult = mvc.perform(get("/user/"+name+"")).andExpect(status().isOk()).andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		System.out.println(result);
	}
	
	@Test
	public void should_return_error_msg_when_input_wrong_name_for_delete() throws Exception {
		String name = "gentle";
		MvcResult mvcResult = mvc.perform(delete("/user/"+name+"")).andExpect(status().isOk()).andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		System.out.println(result);
	}
	
	@Test
	public void should_return_delete_count_when_input_right_name_for_delete() throws Exception {
		String name = "gentle";
		MvcResult mvcResult = mvc.perform(delete("/user/"+name+"")).andExpect(status().isOk()).andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		System.out.println(result);
	}
	
	@Test
	public void should_return_all_user_when_select_all_user() throws Exception {
		MvcResult mvcResult = mvc.perform(get("/users")).andExpect(status().isOk()).andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		System.out.println(result);
	}
	
}
