/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.customer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AttachmentService;
import services.ChirpService;
import services.CustomerService;
import controllers.AbstractController;
import domain.Attachment;
import domain.Chirp;
import domain.Customer;
import forms.ChirpForm;

@Controller
@RequestMapping("/chirp/customer")
public class ChirpCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ChirpService		chirpService;

	@Autowired
	private AttachmentService	attachmentService;

	@Autowired
	private CustomerService		customerService;


	// Constructors -----------------------------------------------------------

	public ChirpCustomerController() {
		super();
	}

	// Methods -----------------------------------------------------------------		

	@RequestMapping(value = "/received", method = RequestMethod.GET)
	public ModelAndView received() {
		ModelAndView result;
		Collection<Chirp> res;

		res = this.chirpService.findReceivedChirpOfPrincipal();

		result = new ModelAndView("chirp/list/received");
		result.addObject("chirps", res);
		result.addObject("requestURI", "chirp/customer/received.do");

		return result;
	}

	@RequestMapping(value = "/sent", method = RequestMethod.GET)
	public ModelAndView sent() {
		ModelAndView result;
		Collection<Chirp> res;

		res = this.chirpService.findSentChirpOfPrincipal();

		result = new ModelAndView("chirp/list/sent");
		result.addObject("chirps", res);
		result.addObject("requestURI", "chirp/customer/sent.do");

		return result;
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam final int chirpId) {
		ModelAndView result;
		Chirp res;
		Collection<Attachment> att;

		res = this.chirpService.findOne(chirpId);
		att = this.attachmentService.findAttachmentsOfChirp(res);

		result = new ModelAndView("chirp/view");
		result.addObject("res", res);
		result.addObject("attachments", att);
		result.addObject("requestURI", "chirp/customer/view.do?chirpId=" + chirpId);

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int chirpId) {
		ModelAndView result;

		this.chirpService.delete(chirpId);

		result = new ModelAndView("redirect:/");

		return result;
	}

	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public ModelAndView reply(@RequestParam final int chirpId) {
		ModelAndView result;
		ChirpForm chirpForm;

		chirpForm = this.chirpService.replyChirp(chirpId);
		result = this.createEditModelAndView(chirpForm);

		return result;
	}

	@RequestMapping(value = "/forward", method = RequestMethod.GET)
	public ModelAndView forward(@RequestParam final int chirpId) {
		ModelAndView result;
		ChirpForm chirpForm;

		chirpForm = this.chirpService.forwardChirp(chirpId);
		result = this.createEditModelAndView(chirpForm);

		return result;
	}

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public ModelAndView write() {
		ModelAndView result;
		ChirpForm chirpForm;

		chirpForm = new ChirpForm();
		result = this.createEditModelAndView(chirpForm);

		return result;
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST, params = "addAttachment")
	public ModelAndView addAttachment(final ChirpForm chirpForm) {
		ModelAndView result;

		chirpForm.addAttachmentSpace();

		result = this.createEditModelAndView(chirpForm);

		return result;
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST, params = "removeAttachment")
	public ModelAndView removeAttachment(final ChirpForm chirpForm) {
		ModelAndView result;

		chirpForm.removeAttachmentSpace();

		result = this.createEditModelAndView(chirpForm);

		return result;
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST, params = "save")
	public ModelAndView write(final ChirpForm chirpForm, final BindingResult bindingResult) {
		ModelAndView result;
		Chirp chirp;
		String error;

		chirp = this.chirpService.reconstruct(chirpForm, bindingResult);
		if (bindingResult.hasErrors()) {
			error = null;
			if (bindingResult.hasFieldErrors("url"))
				error = "chirp.url.error";
			result = this.createEditModelAndView(chirpForm, error);
		} else
			try {
				this.chirpService.save(chirp, chirpForm.getAttachments());
				result = new ModelAndView("redirect:sent.do");
			} catch (final IllegalArgumentException e) {
				result = this.createEditModelAndView(chirpForm, e.getMessage());
			}

		return result;
	}
	///////////////////////////////////////////

	protected ModelAndView createEditModelAndView(final ChirpForm chirpForm) {
		ModelAndView result;

		result = this.createEditModelAndView(chirpForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ChirpForm chirpForm, final String message) {
		ModelAndView result;
		Collection<Customer> customers;

		result = new ModelAndView("chirp/write");
		customers = this.customerService.findAll();

		result.addObject("customers", customers);
		result.addObject("chirpForm", chirpForm);
		result.addObject("message", message);
		result.addObject("requestURI", "chirp/customer/write.do");

		return result;
	}
}
