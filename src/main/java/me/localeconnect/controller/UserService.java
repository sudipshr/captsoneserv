package me.localeconnect.controller;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;

import me.localeconnect.model.User;

public class UserService {

	DynamoDBMapper mapper = DALUtil.getDynamoDBMapper();

	public void createUser(Object model) {

		mapper.save(model);

	}
	
	public void deleteUser(Object model) {

		mapper.save(model);

	}

	public Object getById(Class<?> classz,String id) {

		return mapper.load(classz, id);

	}
	
	public User getUserById(String id) {

		return (User)getById(User.class, id);

	}
	
	public User getUserName(String userName) {
		
		User partitionKey = new User();

		partitionKey.setUserName(userName);
		DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>()
		    .withHashKeyValues(partitionKey);
		queryExpression.setConsistentRead(false);

		List<User> itemList = mapper.query(User.class, queryExpression);

		for (int i = 0; i < itemList.size(); i++) {
		    System.out.println(itemList.get(i).getId());
		    System.out.println(itemList.get(i).getEmail());
		    return itemList.get(i);
		}

		
		return partitionKey;

	}

}
