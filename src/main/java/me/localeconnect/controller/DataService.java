package me.localeconnect.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;

import me.localeconnect.model.Event;
import me.localeconnect.model.Preference;
import me.localeconnect.model.User;

@Service
public class DataService {
	
	private static final Logger logger = LoggerFactory.getLogger(EventController.class);

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
	

	public List<Event> getEventByPreference(String preference) {

/*		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();

		Map<String, Condition> scanFilter = new HashMap<String, Condition>();
		Condition scanCondition = new Condition().withComparisonOperator(ComparisonOperator.CONTAINS.toString())
				.withAttributeValueList(new AttributeValue().withS(preference));

		scanFilter.put("prefId", scanCondition);

		scanExpression.setScanFilter(scanFilter);

		List<Event> results = mapper.scan(Event.class, scanExpression);
		
		List<Event> events = new ArrayList<>();
		
		for(Event event:results){
			events.add(event);
			if(events.size() > 10) break;
		}
		
		logger.info("getEventByPreference: "+results.size());*/
		

		return getEvents("prefId", preference, ComparisonOperator.CONTAINS);
	}
	
	public List<Event> getEventByInitiatingUser(String userId) {

		return getEvents("initiatingUserId", userId, ComparisonOperator.EQ);

	}
	
	public List<Event> getEventByAcceptingUser(String userId) {

		return getEvents("acceptingUserId", userId, ComparisonOperator.EQ);

	}
	
	public List<Event> getEvents(String fieldName, String fieldValue, ComparisonOperator op ) {

		logger.info("getEvents fieldName:"+fieldName+":");
		logger.info("getEvents fieldValue:"+fieldValue+":");
		logger.info("getEvents op:"+op);
		
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();

		Map<String, Condition> scanFilter = new HashMap<String, Condition>();
		Condition scanCondition = new Condition().withComparisonOperator(op.toString())
				.withAttributeValueList(new AttributeValue().withS(fieldValue));

		scanFilter.put(fieldName, scanCondition);

		scanExpression.setScanFilter(scanFilter);

		List<Event> results = mapper.scan(Event.class, scanExpression);
		
		List<Event> events = new ArrayList<>();
		
		for(Event event:results){
			events.add(event);
			if(events.size() > 10) break;
		}
		
		logger.info("getEvents: "+results.size());
		

		return events;
	}
	
	public List<Preference> getPreferenceById(String userId) {

		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();

		Map<String, Condition> scanFilter = new HashMap<String, Condition>();
		Condition scanCondition = new Condition().withComparisonOperator(ComparisonOperator.CONTAINS.toString())
				.withAttributeValueList(new AttributeValue().withS(userId));

		scanFilter.put("userId", scanCondition);

		scanExpression.setScanFilter(scanFilter);

		List<Preference> results = mapper.scan(Preference.class, scanExpression);
		
		List<Preference> preferences = new ArrayList<>();
		
		for(Preference pref:results){
			preferences.add(pref);
			
		}
		
		logger.info("getPreferenceById: "+results.size());
		

		return preferences;
	}

}
