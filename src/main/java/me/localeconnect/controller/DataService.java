package me.localeconnect.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;

import me.localeconnect.model.User;

@Service
public class DataService {

	DynamoDBMapper mapper = DALUtil.getDynamoDBMapper();

	public void save(Object model) {

		mapper.save(model);

	}

	public void delete(Object model) {

		mapper.delete(model);

	}

	public Object getById(Class<?> classz, String id) {

		return mapper.load(classz, id);

	}

	public User getUserById(String id) {

		return (User) getById(User.class, id);

	}

	public User getUserByUserName(String userName) {
		return getUserByUserName(userName, null);
	}

	public User getUserByUserName(String userName, String password) {

		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();

		Map<String, Condition> scanFilter = new HashMap<String, Condition>();
		Condition scanCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
				.withAttributeValueList(new AttributeValue().withS(userName));

		scanFilter.put("userName", scanCondition);

		scanCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
				.withAttributeValueList(new AttributeValue().withS(password));
		if (password != null)
			scanFilter.put("password", scanCondition);

		scanExpression.setScanFilter(scanFilter);

		List<User> results = mapper.scan(User.class, scanExpression);

		if (results != null && !results.isEmpty()) {
			return results.get(0);
		}

		/*
		 * User partitionKey = new User();
		 * 
		 * partitionKey.setUserName(userName); DynamoDBQueryExpression<User>
		 * queryExpression = new DynamoDBQueryExpression<User>()
		 * .withHashKeyValues(partitionKey);
		 * queryExpression.setConsistentRead(false);
		 * 
		 * List<User> itemList = mapper.query(User.class, queryExpression);
		 * 
		 * for (int i = 0; i < itemList.size(); i++) {
		 * System.out.println(itemList.get(i));
		 * 
		 * return itemList.get(i); }
		 */

		return null;

	}
	
	

	public User getEventByUserId(String userId) {

		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();

		Map<String, Condition> scanFilter = new HashMap<String, Condition>();
		Condition scanCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString())
				.withAttributeValueList(new AttributeValue().withS(userId));

		scanFilter.put("userName", scanCondition);

		
		scanExpression.setScanFilter(scanFilter);

		List<User> results = mapper.scan(User.class, scanExpression);

		results.size();
		if (results != null && !results.isEmpty()) {
			return results.get(0);
		}

		/*
		 * User partitionKey = new User();
		 * 
		 * partitionKey.setUserName(userName); DynamoDBQueryExpression<User>
		 * queryExpression = new DynamoDBQueryExpression<User>()
		 * .withHashKeyValues(partitionKey);
		 * queryExpression.setConsistentRead(false);
		 * 
		 * List<User> itemList = mapper.query(User.class, queryExpression);
		 * 
		 * for (int i = 0; i < itemList.size(); i++) {
		 * System.out.println(itemList.get(i));
		 * 
		 * return itemList.get(i); }
		 */

		return null;

	}

}
