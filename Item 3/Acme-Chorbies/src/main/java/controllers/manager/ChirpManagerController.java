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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChirpService;
import services.EventService;
import controllers.AbstractController;
import domain.Chirp;
import domain.Event;
import forms.ChirpBroadcastForm;

@Controller
@RequestMapping("/chirp/manager")
public class ChirpManagerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ChirpService	chirpService;

	@Autowired
	private EventService	eventService;


	// Constructors -----------------------------------------------------------

	public ChirpManagerController() {
		super();
	}

	// Methods -----------------------------------------------------------------		

	@RequestMapping(value = "/broadcast", method = RequestMethod.GET)
	public ModelAndView write(@RequestParam(required = true) final Integer eventId) {
		ModelAndView result;
		ChirpBroadcastForm chirpBroadcastForm;

		chirpBroadcastForm = new ChirpBroadcastForm();
		chirpBroadcastForm.setEvent(eventId);
		result = this.createEditModelAndView(chirpBroadcastForm);

		return result;
	}

	@RequestMapping(value = "/broadcast", method = RequestMethod.POST, params = "addAttachment")
	public ModelAndView addAttachment(final ChirpBroadcastForm chirpBroadcastForm) {
		ModelAndView result;

		chirpBroadcastForm.addAttachmentSpace();

		result = this.createEditModelAndView(chirpBroadcastForm);

		return result;
	}

	@RequestMapping(value = "/broadcast", method = RequestMethod.POST, params = "removeAttachment")
	public ModelAndView removeAttachment(final ChirpBroadcastForm chirpBroadcastForm) {
		ModelAndView result;

		chirpBroadcastForm.removeAttachmentSpace();

		result = this.createEditModelAndView(chirpBroadcastForm);

		return result;
	}

	@RequestMapping(value = "/broadcast", method = RequestMethod.POST, params = "save")
	public ModelAndView write(final ChirpBroadcastForm chirpBroadcastForm, final BindingResult bindingResult) {
		ModelAndView result;
		Collection<Chirp> chirps;
		String error;
		chirps = this.chirpService.reconstruct(chirpBroadcastForm, bindingResult);
		if (bindingResult.hasErrors()) {
			error = null;
			if (bindingResult.hasFieldErrors("url"))
				error = "chirp.url.error";
			result = this.createEditModelAndView(chirpBroadcastForm, error);
		} else
			try {
				this.chirpService.save(chirps, chirpBroadcastForm.getAttachments(), chirpBroadcastForm.getEvent());
				result = new ModelAndView("redirect:/event/manager/list.do");
			} catch (final IllegalArgumentException e) {
				result = this.createEditModelAndView(chirpBroadcastForm, e.getMessage());
			}

		return result;
	}
	///////////////////////////////////////////

	protected ModelAndView createEditModelAndView(final ChirpBroadcastForm chirpBroadcastForm) {
		ModelAndView result;

		result = this.createEditModelAndView(chirpBroadcastForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ChirpBroadcastForm chirpBroadcastForm, final String message) {
		ModelAndView result;
		Collection<Event> events;

		result = new ModelAndView("chirp/broadcast");
		events = this.eventService.findAllFromPrincipalManager();
		result.addObject("chirpBroadcastForm", chirpBroadcastForm);

		result.addObject("message", message);
		result.addObject("events", events);
		result.addObject("requestURI", "chirp/manager/broadcast.do");

		return result;
	}
}
