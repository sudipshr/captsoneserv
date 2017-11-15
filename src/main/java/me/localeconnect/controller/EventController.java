package me.localeconnect.controller;

import java.util.ArrayList;
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

import me.localeconnect.model.Event;
import me.localeconnect.model.Preference;



@Controller
@RequestMapping("/eventserv")
public class EventController {
	
	private static final Logger logger = LoggerFactory.getLogger(EventController.class);
	
	@Autowired
	private DataService service;
	
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Event> createEvent(@RequestBody Event event) {
		
		logger.info(event.toString());
		
		service.save(event);
		logger.info(event.toString());
		return new ResponseEntity<Event>(event, HttpStatus.OK);
	    
	}
	
	@RequestMapping(value = "", method = RequestMethod.DELETE)
	public ResponseEntity<Event> deleteEvent(@RequestBody Event event) {
		
		logger.info(event.toString());
		
		service.delete(event);
		logger.info("deleted");
		return new ResponseEntity<Event>(event, HttpStatus.OK);
	    
	}
	
	@RequestMapping(value = "", method = RequestMethod.PUT)
	public ResponseEntity<Event> updateEvent(@RequestBody Event event) {
		
		logger.info(event.toString());
		service.save(event);
		logger.info(event.toString());
		return new ResponseEntity<Event>(event, HttpStatus.OK);
	    
	}
	
	
	@RequestMapping(value = "/event", method = RequestMethod.GET)
	public ResponseEntity<Event> getEvent(@RequestBody Event event) {
		logger.info(event.toString());
		service.getById(Event.class, event.getId());
		logger.info(event.toString());
		return new ResponseEntity<Event>(event, HttpStatus.OK);
	    
	}
	
	
	@RequestMapping(value = "/events", method = RequestMethod.GET)
	public ResponseEntity<List<Event>> getEvents(@RequestParam String userId) {
		logger.info("getEvents: "+userId);
		List<Event> events = service.getEventByInitiatingUser(userId);
		logger.info("getEvents: "+events);
		
		return new ResponseEntity<List<Event>>(events, HttpStatus.OK);
	    
	}

	@RequestMapping(value = "/matchingEvents", method = RequestMethod.GET)
	public ResponseEntity<List<Event>> getMatchingEvents(@RequestParam String userId) {
		logger.info("getEvents: "+userId);
		
		List<Event> events = new ArrayList<>();
		for (Preference preference: service.getPreferenceById(userId)){
			events.addAll(service.getEventByPreference(preference.getType()));
		}

		logger.info("getEvents: "+events);
		
		return new ResponseEntity<List<Event>>(events, HttpStatus.OK);
	    
	}
}
