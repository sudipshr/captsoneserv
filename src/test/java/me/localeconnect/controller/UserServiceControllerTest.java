package me.localeconnect.controller;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.localeconnect.model.Event;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LocaleConnectConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
public class UserServiceControllerTest {

	@LocalServerPort
	private int port;

	@Value("${local.management.port}")
	private int mgt;

	@Autowired
	private TestRestTemplate testRestTemplate;
	
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
	public void testRegister() {
		
		ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.port + "/register", Map.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void testEvent() {
		
		
		
		Event event = new Event();
		event.setAcceptingUserId("accepting user");
		event.setAddress("address");
		
		HttpEntity<Event> request = new HttpEntity<>(event);
		
		
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = "{\"address\" : \"my address\"}";
		
		ResponseEntity<Event> entity = this.testRestTemplate.exchange("http://localhost:" + this.port + "/eventserv/test", HttpMethod.POST, 
				request, Event.class);
		
		System.out.println("after rest call"+entity.getBody());

		//JSON from file to Object
		//Staff obj = mapper.readValue(new File("c:\\file.json"), Staff.class);

		//JSON from URL to Object
		//Staff obj = mapper.readValue(new URL("http://mkyong.com/api/staff.json"), Staff.class);

		//JSON from String to Object
		Event obj;
		try {
			obj = mapper.readValue(jsonInString, Event.class);
			//System.out.println(obj.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


			
	}

	@Test
	public void testAutheticateUser() {
		//fail("Not yet implemented");
	}

	@Test
	public void testEvents() {
		
		ResponseEntity<Object[]> responseEntity = this.testRestTemplate.getForEntity("http://localhost:" + this.port + "/eventserv/events?userId=test", Object[].class);
        Object[] objects = responseEntity.getBody();

        Arrays.asList((Event[])(objects));
	}

	@Test
	public void testEventsNew() {
		
		ResponseEntity<Event[]> responseEntity = this.testRestTemplate.getForEntity("http://localhost:" + this.port + "/eventserv/events?userId=test", Event[].class);
		Event[] objects = responseEntity.getBody();

        Arrays.asList(objects);
	}
}
