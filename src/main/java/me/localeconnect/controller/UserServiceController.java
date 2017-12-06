package me.localeconnect.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import me.localeconnect.model.Event;
import me.localeconnect.model.Preference;
import me.localeconnect.model.User;

@Controller
@RequestMapping("/userserv")
public class UserServiceController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceController.class);
	
	@Autowired
	DataService service;
	
	@RequestMapping(method = RequestMethod.POST, path = "/register")
	public @ResponseBody User registerBackup(@RequestBody User user) {

		logger.info(user.toString());
		user.setJoinDate(new Date());
		
		User dbUser = service.getUserByUserName(user.getUserName(), null);
		
		if (dbUser == null)
			service.save(user);
		
		logger.info(user.toString());

		return user;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/auth")
	public @ResponseBody ResponseEntity<User> auth(@RequestBody User user) {

		try {
			
			

			logger.info(user.toString());
			
			String password = user.getPassword()+"";
			
			user = service.getUserByUserName(user.getUserName(), null);
			
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
		
		User dbUser = service.getUserByUserName(userName, null);
		
		if (dbUser == null)
			service.save(user);

		return user;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/authbackup")
	public @ResponseBody ResponseEntity<User> autheticateUserBackup(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password) {

		User user = null;

		try {

			user = service.getUserByUserName(userName, password);
			
			if (user != null && password.equals(user.getPassword())){
				
				return new ResponseEntity<>(user, org.springframework.http.HttpStatus.OK);
				
			}
			
			

		} catch (Exception e) {
			System.err.println("GetItem failed.");
			System.err.println(e.getMessage());
		}
		
		return new ResponseEntity<>(null, org.springframework.http.HttpStatus.UNAUTHORIZED);
	}
	
	
	@RequestMapping(value = "/createPreference", method = RequestMethod.POST)
	public ResponseEntity<Preference> createPreference(@RequestBody Preference preference) {
		
		logger.info(preference.toString());
		
		service.save(preference);
		logger.info(preference.toString());
		return new ResponseEntity<Preference>(preference, HttpStatus.OK);
	    
	}
	
	@RequestMapping(value = "/deletePreference", method = RequestMethod.DELETE)
	public ResponseEntity<String> deletePreference(@RequestBody Preference preference) {
		
		logger.info(preference.toString());
		
		service.delete(preference);
		logger.info("deleted preference");
		return new ResponseEntity<String>("Deleted", HttpStatus.OK);
	    
	}
	
	/**
	 * Return List of preferences for a given user
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/getPreferences", method = RequestMethod.GET)
	public ResponseEntity<List<Preference>> getPreferences(@RequestParam String userId) {
		logger.info("getPreferences: "+userId);
		List<Preference> preferences = service.getPreferenceById(userId);
		logger.info("getPreferences: "+preferences);
		
		return new ResponseEntity<List<Preference>>(preferences, HttpStatus.OK);
	    
	}



	@RequestMapping(method = RequestMethod.POST, path = "/create")
	public @ResponseBody boolean createTable(@RequestParam(value = "tableName", required = true) String tableName) {

		DALUtil.createTable(User.class);

		return true;

	}


}
