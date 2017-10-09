package me.localeconnect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.api.PutItemApi;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;

import me.localeconnect.model.User;

@Controller
@RequestMapping("/userserv")
public class UserServiceController {

	@RequestMapping(method = RequestMethod.POST, path = "/auth")
	public @ResponseBody User autheticateUser(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password) {

		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);

		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
				new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2")).build();
		
		com.amazonaws.services.dynamodbv2.document.DynamoDB dynamoDB = new com.amazonaws.services.dynamodbv2.document.DynamoDB(client);

		Table table = dynamoDB.getTable("user");

		Item item = new Item()
		    .withPrimaryKey("id", 123)
		    .withString("Title", "Bicycle 123")
		    .withString("Description", "123 description")
		    .withString("BicycleType", "Hybrid")
		    .withString("Brand", "Brand-Company C")
		    .withNumber("Price", 500);

		PutItemOutcome outcome = table.putItem(item);
				

		return user;
	}

}
