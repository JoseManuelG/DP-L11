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

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import services.EventService;
import domain.Event;

@Controller
@RequestMapping("/event")
public class EventController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private EventService	eventService;

	@Autowired
	private ChorbiService	chorbiService;


	// Constructors -----------------------------------------------------------

	public EventController() {
		super();
	}

	// Methods -----------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(defaultValue = "false") final Boolean sorted) {
		ModelAndView result;
		List<Event> events;
		String requestURI;

		events = new ArrayList<Event>(this.eventService.findNextMonthEventsWithPlaces());
		requestURI = "event/list.do";
		if (sorted) {
			this.eventService.sort(events);
			requestURI += "?ordered=true";
		}

		result = new ModelAndView("event/list");
		result.addObject("events", events);
		result.addObject("sorted", sorted);
		result.addObject("requestURI", requestURI);

		return result;
	}

	@RequestMapping(value = "/list/all", method = RequestMethod.GET)
	public ModelAndView listAll(@RequestParam(defaultValue = "false") final Boolean sorted) {
		ModelAndView result;
		String requestURI;
		List<Event> events, eventsCloseToFinish;

		events = new ArrayList<Event>(this.eventService.findAll());
		eventsCloseToFinish = new ArrayList<Event>(this.eventService.findNextMonthEventsWithPlaces());

		requestURI = "event/list/all.do";
		if (sorted) {
			this.eventService.sort(events);
			requestURI += "?sorted=true";
		}

		result = new ModelAndView("event/list/all");
		result.addObject("events", events);
		result.addObject("eventsCloseToFinish", eventsCloseToFinish);
		result.addObject("sorted", sorted);
		result.addObject("requestURI", requestURI);

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

		registered = false;
		try {
			this.chorbiService.findChorbiByPrincipal();
			registered = this.eventService.checkPrincipalIsRegistered(event);
		} catch (final IllegalArgumentException e) {
		}

		result = new ModelAndView("event/view");
		result.addObject("event", event);
		result.addObject("siteFree", siteFree);
		result.addObject("expired", expired);
		result.addObject("registered", registered);
		result.addObject("requestURI", "event/chorbi/view.do?eventId=" + eventId);

		return result;
	}
}
