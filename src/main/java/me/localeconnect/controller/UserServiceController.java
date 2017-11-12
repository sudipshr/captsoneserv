package me.localeconnect.controller;

import java.util.Date;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import me.localeconnect.model.User;

@Controller
@RequestMapping("/userserv")
public class UserServiceController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceController.class);
	
	@Autowired
	DataService userService;
	
	@RequestMapping(method = RequestMethod.POST, path = "/register")
	public @ResponseBody User registerBackup(@RequestBody User user) {

		logger.info(user.toString());
		user.setJoinDate(new Date());
		
		User dbUser = userService.getUserByUserName(user.getUserName(), null);
		
		if (dbUser == null)
			userService.save(user);
		
		logger.info(user.toString());

		return user;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/auth")
	public @ResponseBody ResponseEntity<User> auth(@RequestBody User user) {

		try {
			
			

			logger.info(user.toString());
			
			String password = user.getPassword()+"";
			
			user = userService.getUserByUserName(user.getUserName(), null);
			
			if (user != null && password.equals(user.getPassword())){
				
				logger.info(user.toString());
				
				return new ResponseEntity<>(user, org.springframework.http.HttpStatus.OK);
				
			}
			
			

		} catch (Exception e) {
			System.err.println("GetItem failed.");
			System.err.println(e.getMessage());
		}
		
		return new ResponseEntity<>(null, org.springframework.http.HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/registerbackup")
	public @ResponseBody User registerBackup(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password) {

		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		user.setJoinDate(new Date());
		
		User dbUser = userService.getUserByUserName(userName, null);
		
		if (dbUser == null)
			userService.save(user);

		return user;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/authbackup")
	public @ResponseBody ResponseEntity<User> autheticateUserBackup(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password) {

		User user = null;

		try {

			user = userService.getUserByUserName(userName, password);
			
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
