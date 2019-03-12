package com.oocl.springbootdemo.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.oocl.springbootdemo.dao.UserDao;
import com.oocl.springbootdemo.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private MongoTemplate template;
	
	@Value("${collection.name}")
	private String collectionName;
	
	@Override
	public User insertUser(User user) {
		// TODO Auto-generated method stub
		return template.save(user, collectionName);
	}

	@Override
	@SuppressWarnings("static-access")
	public User getUserByName(String name) {
		// TODO Auto-generated method stub
		Query query = new Query().addCriteria(new Criteria().where("name").is(name));
		List<User> list = template.find(query, User.class, collectionName);
		if(list == null || list.size() == 0) {
			throw new NullPointerException("can not find user");
		}else {
			return list.get(0);
		}
	}

	@Override
	@SuppressWarnings("static-access")
	public int updateUserByName(String name, User user) {
		// TODO Auto-generated method stub
		Query query = new Query().addCriteria(new Criteria().where("name").is(name));
		Update update = new Update().set("account", user.getAccount()).
					set("name", user.getName()).set("gender", user.getGender());
		UpdateResult result = template.updateFirst(query, update, collectionName);
		int updateCount = (int) result.getModifiedCount();
		if(updateCount < 1) {
			throw new NullPointerException("can not find user");
		}else{
			return updateCount;
		}
	}

	@Override
	@SuppressWarnings("static-access")
	public int deleteUserByName(String name) {
		// TODO Auto-generated method stub
		Query query = new Query().addCriteria(new Criteria().where("name").is(name));
		DeleteResult result = template.remove(query, collectionName);
		int deleteCount = (int) result.getDeletedCount();
		if(deleteCount < 1) {
			throw new NullPointerException("can not find user");
		}else{
			return deleteCount;
		}
	}

}
