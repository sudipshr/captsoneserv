package me.localeconnect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import me.localeconnect.model.User;

@Controller
@RequestMapping("/userserv")
public class UserServiceController {
	
	UserService userService;

	@RequestMapping(method = RequestMethod.POST, path = "/register")
	public @ResponseBody User register(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password) {

		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);

		userService.createUser(user);

		return user;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/auth")
	public @ResponseBody User autheticateUser(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password) {

		User user = null;

		try {

			user = userService.getUserName(userName);
			
			

		} catch (Exception e) {
			System.err.println("GetItem failed.");
			System.err.println(e.getMessage());
		}

		return user;
	}



	@RequestMapping(method = RequestMethod.POST, path = "/create")
	public @ResponseBody boolean createTable(@RequestParam(value = "tableName", required = true) String tableName) {

		DALUtil.createTable(User.class);

		return true;

	}


}
