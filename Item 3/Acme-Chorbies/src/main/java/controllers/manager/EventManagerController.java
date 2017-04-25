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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.EventService;
import services.RegisterService;
import controllers.AbstractController;
import domain.Event;

@Controller
@RequestMapping("/event/manager")
public class EventManagerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private EventService	eventService;

	@Autowired
	private RegisterService	registerService;


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

	// Create ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Event event;

		event = this.eventService.create();
		result = this.createEditModelAndView(event);

		return result;

	}

	// Edit -----------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int eventId) {
		final Event event = this.eventService.findOne(eventId);
		final ModelAndView result = this.createEditModelAndView(event);
		return result;

	}

	// Save ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Event originalEvent, final BindingResult binding) {
		ModelAndView result = null;
		Event event;

		event = this.eventService.reconstruct(originalEvent, binding);
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.createEditModelAndView(originalEvent);
		} else
			try {
				this.eventService.save(event);
				this.eventService.notifyChangesToAssistantChorbies(event);
				result = new ModelAndView("redirect:/event/manager/list.do");
			} catch (final IllegalArgumentException exception) {
				result = this.createEditModelAndView(originalEvent, exception.getMessage());
			}
		return result;

	}

	// Delete ---------------------------------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public @ResponseBody
	ModelAndView save(final Event ev) {
		ModelAndView result = null;
		final Event event = this.eventService.findOne(ev.getId());

		try {
			this.eventService.notifyChangesToAssistantChorbies(event);
			this.registerService.deleteRegistersForEvent(event);
			this.eventService.delete(event);
			result = new ModelAndView("redirect:/event/manager/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(event, "event.commit.error");
		}

		return result;
	}

	//Ancillary methods ----------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Event event) {
		ModelAndView result;

		result = this.createEditModelAndView(event, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Event event, final String message) {
		ModelAndView result;
		result = new ModelAndView("event/manager/edit");
		result.addObject("event", event);
		result.addObject("message", message);

		return result;
	}
}
