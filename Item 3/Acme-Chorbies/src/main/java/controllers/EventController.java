/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EventService;
import antlr.debug.Event;

@Controller
@RequestMapping("/event")
public class EventController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private EventService	eventService;


	// Constructors -----------------------------------------------------------

	public EventController() {
		super();
	}

	// Methods -----------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView received() {
		ModelAndView result;
		Collection<Event> events;

		events = this.eventService.findNextMonthEventsWithPlaces();

		result = new ModelAndView("event/list");
		result.addObject(events);
		result.addObject("requestURI", "event/list.do");

		return result;
	}

	@RequestMapping(value = "/list/all", method = RequestMethod.GET)
	public ModelAndView sent() {
		ModelAndView result;
		Collection<Event> events;

		events = this.eventService.findAll();

		result = new ModelAndView("event/list/all");
		result.addObject(events);
		result.addObject("requestURI", "event/list/all.do");

		return result;
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam final int eventId) {
		ModelAndView result;
		Event event;
		Boolean siteFree, expired, registered;

		event = this.eventService.findOne(eventId);
		expired = this.eventService.checkExpired(event);
		siteFree = this.eventService.checkSiteFree(event);
		registered = this.eventService.checkPrincipalRegistered(event);

		result = new ModelAndView("event/view");
		result.addObject(event);
		result.addObject(siteFree);
		result.addObject(expired);
		result.addObject(registered);
		result.addObject("requestURI", "event/chorbi/view.do?eventId=" + eventId);

		return result;
	}
}
