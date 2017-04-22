/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.chorbi;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.EventService;
import controllers.AbstractController;
import domain.Event;

@Controller
@RequestMapping("/event/manager")
public class EventChorbiController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private EventService	eventService;


	// Constructors -----------------------------------------------------------

	public EventChorbiController() {
		super();
	}

	// Methods -----------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView received() {
		ModelAndView result;
		Collection<Event> events;

		events = this.eventService.findAllFromPrincipalManager();

		result = new ModelAndView("event/list");
		result.addObject("events", events);
		result.addObject("requestURI", "event/chorbi/list.do");

		return result;
	}
}
