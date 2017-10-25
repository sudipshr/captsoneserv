package me.localeconnect.controller;

import java.util.Date;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import me.localeconnect.model.User;

@Controller
@RequestMapping("/userserv")
public class UserServiceController {
	
	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.POST, path = "/register")
	public @ResponseBody User register(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password) {

		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		user.setJoinDate(new Date());

		userService.createUser(user);

		return user;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/auth")
	public @ResponseBody ResponseEntity<User> autheticateUser(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password) {

		User user = null;

		try {

			user = userService.getUserByUserName(userName);
			
			if (user != null && password.equals(user.getPassword())){
				
				return new ResponseEntity<>(user, org.springframework.http.HttpStatus.OK);
				
			}
			
			

		} catch (Exception e) {
			System.err.println("GetItem failed.");
			System.err.println(e.getMessage());
		}
		
		return new ResponseEntity<>(null, org.springframework.http.HttpStatus.UNAUTHORIZED);
	}



	@RequestMapping(method = RequestMethod.POST, path = "/create")
	public @ResponseBody boolean createTable(@RequestParam(value = "tableName", required = true) String tableName) {

		DALUtil.createTable(User.class);

		return true;

	}


}
