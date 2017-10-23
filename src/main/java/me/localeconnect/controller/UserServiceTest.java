package me.localeconnect.controller;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import me.localeconnect.model.User;

public class UserServiceTest {
	
	
	UserService service = new UserService();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateUser() {
		User user = new User();
		user.setDeviceId("1234");
		user.setEmail("a@a.com");
		user.setUserName("a@a.com");
		user.setJoinDate(new Date(System.currentTimeMillis()));
		
		service.createUser(user);
		user = new User();
		user.setDeviceId("12dddd34");
		user.setEmail("a@ddddddddddddda.com");
		user.setUserName("ccccccccccccc");
		user.setJoinDate(new Date(System.currentTimeMillis()));
		service.createUser(user);
		
		user = new User();
		user.setDeviceId("12dddd34");
		user.setEmail("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx@a.com");
		user.setUserName("sdfsdfsf");
		user.setJoinDate(new Date(System.currentTimeMillis()));
		service.createUser(user);
		
	}

	@Test
	public void testDeleteUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetById() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserById() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserEmail() {
		System.out.println(service.getUserName("a@a.com"));
	}

}
