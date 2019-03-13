package com.oocl.springbootdemo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


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
	public void should_return_user_name_when_input_user_for_insert() throws Exception {

		String JsonString = "{\"account\": \"gentle@oocl.com\",\"gender\": \"male\",\"name\": \"gentle\"}";
		MvcResult mvcResult = mvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(JsonString))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		Assert.assertTrue(result.contains("gentle"));
	}

	@Test
	public void should_return_error_msg_when_input_wrong_name_for_select() throws Exception {
		String name = "unRegistered";
		MvcResult mvcResult = mvc.perform(get("/user/"+name+"")).andExpect(status().isOk()).andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		Assert.assertTrue(result.contains("fail"));
	}
	
	@Test
	public void should_return_user_when_input_right_name_for_select() throws Exception {
		String name = "gentle";
		MvcResult mvcResult = mvc.perform(get("/user/"+name+"")).andExpect(status().isOk()).andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		Assert.assertTrue(result.contains("gentle"));
	}
	
	@Test
	public void should_return_error_msg_when_input_wrong_name_for_delete() throws Exception {
		String name = "unRegistered";
		MvcResult mvcResult = mvc.perform(delete("/user/"+name+"")).andExpect(status().isOk()).andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		Assert.assertTrue(result.contains("fail"));
	}

	@Test
	public void should_return_error_msg_when_input_wrong_name_for_update() throws Exception {
		String name = "gentle";
		String JsonString = "{\"account\": \"gentle1@oocl.com\",\"gender\": \"male\",\"name\": \"gentle\"}";
		MvcResult mvcResult = mvc.perform(put("/user/"+name+"").contentType(MediaType.APPLICATION_JSON).content(JsonString))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		Assert.assertTrue(result.contains("\"updateCount\":1"));
	}

	@Test
	public void should_return_delete_count_when_input_right_name_for_delete() throws Exception {
		String name = "gentle";
		MvcResult mvcResult = mvc.perform(delete("/user/"+name+"")).andExpect(status().isOk()).andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		Assert.assertTrue(result.contains("\"updateCount\":1"));
	}
	
	@Test
	public void should_return_all_user_when_select_all_user() throws Exception {
		MvcResult mvcResult = mvc.perform(get("/users")).andExpect(status().isOk()).andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		Assert.assertNotNull(result);
	}

	@Test
	public void should_return_config_when_testAutoConfig() throws Exception {
		MvcResult mvcResult = mvc.perform(get("/autoconfig")).andExpect(status().isOk()).andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		Assert.assertEquals(result,"{\"code\":200,\"msg\":\"SUCCESS\"}");
	}
}
