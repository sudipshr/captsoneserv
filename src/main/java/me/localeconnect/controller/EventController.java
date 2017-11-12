package me.localeconnect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import me.localeconnect.model.Event;



@Controller
@RequestMapping("/eventserv")
public class EventController {
	
	@Autowired
	private DataService service;
	
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Event> createEvent(@RequestBody Event event) {
		
		//event.setId("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
		
		service.save(event);
		return new ResponseEntity<Event>(event, HttpStatus.OK);
	    
	}
	
	@RequestMapping(value = "", method = RequestMethod.DELETE)
	public ResponseEntity<Event> deleteEvent(@RequestBody Event event) {
		
		//event.setId("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
		
		service.delete(event);
		return new ResponseEntity<Event>(event, HttpStatus.OK);
	    
	}
	
	@RequestMapping(value = "", method = RequestMethod.PUT)
	public ResponseEntity<Event> updateEvent(@RequestBody Event event) {
		
		
		
		service.save(event);
		return new ResponseEntity<Event>(event, HttpStatus.OK);
	    
	}
	
	
	@RequestMapping(value = "/event", method = RequestMethod.GET)
	public ResponseEntity<Event> getEvent(@RequestBody Event event) {
		
		service.getById(Event.class, event.getId());
		return new ResponseEntity<Event>(event, HttpStatus.OK);
	    
	}
	
	@RequestMapping(value = "/eventss", method = RequestMethod.GET)
	public ResponseEntity<Event> getEvents(@RequestBody Event event) {
		
		service.getById(Event.class, event.getId());
		return new ResponseEntity<Event>(event, HttpStatus.OK);
	    
	}
	
	@RequestMapping(value = "/events", method = RequestMethod.GET)
	public ResponseEntity<List<Event>> getEvents(@RequestParam String userId) {
		
		List<Event> events = service.getEventByPreference("hangout");
		
		return new ResponseEntity<List<Event>>(events, HttpStatus.OK);
	    
	}

}
