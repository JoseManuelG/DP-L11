/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EventService;
import controllers.AbstractController;
import domain.Event;

@Controller
@RequestMapping("/event/manager")
public class EventManagerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private EventService	eventService;


	// Constructors -----------------------------------------------------------

	public EventManagerController() {
		super();
	}

	// Methods -----------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView received(@RequestParam(defaultValue = "false") final Boolean sorted) {
		ModelAndView result;
		List<Event> events;
		String requestURI;

		events = new ArrayList<Event>(this.eventService.findAllFromPrincipalManager());
		requestURI = "event/manager/list.do";
		if (sorted) {
			this.eventService.sort(events);
			requestURI += "?sorted=true";
		}

		result = new ModelAndView("event/list");
		result.addObject("events", events);
		result.addObject("sorted", sorted);
		result.addObject("requestURI", requestURI);

		return result;
	}
}
