package me.localeconnect.controller;

import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import me.localeconnect.model.Event;
import me.localeconnect.model.User;

public class DataServiceTest {
	
	
	DataService service = new DataService();

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
		user.setUserName("sudip");
		user.setPassword("sudip");
		user.setJoinDate(new Date(System.currentTimeMillis()));
		
		service.save(user);
		user = new User();
		user.setDeviceId("12dddd34");
		user.setEmail("a@ddddddddddddda.com");
		user.setUserName("shrestha");
		user.setPassword("sudip");
		user.setJoinDate(new Date(System.currentTimeMillis()));
		service.save(user);
		
		user = new User();
		user.setDeviceId("12dddd34");
		user.setEmail("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx@a.com");
		user.setUserName("saisha");
		user.setPassword("sudip");
		user.setJoinDate(new Date(System.currentTimeMillis()));
		service.save(user);
		
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
	public void testGetUserByUserName() {
		try {

			User user = service.getUserByUserName("sudip");
			
			System.out.println(user);
			Thread.sleep(5000);
			user = service.getUserByUserName("sudip", "sudip");
			
			System.out.println(user);
			

		} catch (Exception e) {
			System.err.println("GetItem failed.");
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void testDelete(){
		
		Event event = new Event();
		event.setEventTime(new Date());
		
		service.save(event);
		
		String id = event.getId();
		
		System.out.println("IDDDDD: "+id);
		
		Event ev = new Event();
		ev.setId(id);
		ev.setEventTime(event.getEventTime());
		
		service.delete(ev);
		
		
		
	}
	
	@Test
	public void testGetEvents(){
		

		
		System.out.println(service.getEventByPreference("hangout"));
		
		
		
	}
	
	@Test
	public void testEventSave(){
		
		Event event = new Event();
		event.setEventTime(new Date());
		event.setPrefId("dating hangout");
		
		
		service.save(event);
		
		String id = event.getId();
		
		System.out.println("IDDDDD: "+id);
		
		Event ev = new Event();
		ev.setId(null);
		ev.setId(null);
		ev.setPrefId("hangout tea coffee");
		ev.setEventTime(event.getEventTime());
		int i = 0;
		while (true){
			event.setId(null);
			service.save(event);
			i++;
			if (i>20) break;
		}
		
		System.out.println("IDDDDD: "+id);
		
		
		
	}

}
