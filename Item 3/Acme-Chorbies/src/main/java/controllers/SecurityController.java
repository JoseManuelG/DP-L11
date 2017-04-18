
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ChorbiService;
import domain.Actor;
import domain.Administrator;
import domain.Chorbi;
import forms.ActorForm;

@Controller
@RequestMapping("/security")
public class SecurityController extends AbstractController {

	//Services------------------------------------------------------------
	@Autowired
	private ChorbiService	chorbiService;

	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		final ActorForm actorForm = new ActorForm();
		result = this.createRegisterModelAndView(actorForm);

		return result;
	}

	// Save ---------------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ActorForm actorForm, final BindingResult binding) {
		ModelAndView result;
		Chorbi chorbi = new Chorbi();

		chorbi = this.chorbiService.reconstruct(actorForm, binding);
		if (binding.hasErrors())
			result = this.createRegisterModelAndView(actorForm);
		else
			try {

				this.chorbiService.save(chorbi);

				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				result = this.createRegisterModelAndView(actorForm, oops.getMessage());
			}

		return result;
	}
	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Actor actor;
		Boolean isAdmin;
		ActorForm actorForm;
		Chorbi chorbi;

		actor = this.actorService.findActorByPrincipal();

		isAdmin = actor instanceof Administrator;

		actorForm = new ActorForm();
		actorForm.setName(actor.getName());
		actorForm.setSurname(actor.getSurname());
		actorForm.setEmail(actor.getEmail());
		actorForm.setPhone(actor.getPhone());
		actorForm.setUserAccount(actor.getUserAccount());
		if (actor instanceof Chorbi) {
			chorbi = this.chorbiService.findChorbiByPrincipal();
			actorForm.setBirthDate(chorbi.getBirthDate());
			actorForm.setPicture(chorbi.getPicture());
			actorForm.setDescription(chorbi.getDescription());
			actorForm.setDesiredRelationship(chorbi.getDesiredRelationship());
			actorForm.setGenre(chorbi.getGenre());
			actorForm.setCountry(chorbi.getCoordinates().getCountry());
			actorForm.setCity(chorbi.getCoordinates().getCity());
			actorForm.setState(chorbi.getCoordinates().getState());
			actorForm.setProvince(chorbi.getCoordinates().getProvince());

		}

		result = this.createEditModelAndView(actorForm, isAdmin);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final ActorForm actorForm, final BindingResult binding) {
		ModelAndView result;
		Actor principal;
		Boolean isAdmin;
		Chorbi chorbiResult;

		chorbiResult = new Chorbi();

		isAdmin = false;
		principal = this.actorService.findActorByPrincipal();
		if (principal instanceof Chorbi)
			chorbiResult = this.chorbiService.reconstruct(actorForm, (Chorbi) principal, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(actorForm, isAdmin);
		else
			try {
				this.chorbiService.save(chorbiResult);
				result = new ModelAndView("redirect:/chorbi/chorbi/myProfile.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(actorForm, isAdmin, oops.getMessage());
			}

		return result;
	}

	// Delete ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final ActorForm actorForm) {
		ModelAndView result;

		try {
			this.chorbiService.delete();

			result = new ModelAndView("redirect:/j_spring_security_logout");

		} catch (final Exception e) {
			result = this.createEditModelAndView(actorForm, false, "chorbi.commit.error");
		}

		return result;

	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createRegisterModelAndView(final ActorForm actorForm) {
		ModelAndView result;

		result = this.createRegisterModelAndView(actorForm, null);

		return result;
	}

	protected ModelAndView createRegisterModelAndView(final ActorForm actorForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("security/register");
		result.addObject("actorForm", actorForm);
		result.addObject("message", message);

		return result;
	}
	protected ModelAndView createEditModelAndView(final ActorForm actorForm, final Boolean isAdmin) {
		ModelAndView result;

		result = this.createEditModelAndView(actorForm, isAdmin, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ActorForm actorForm, final Boolean isAdmin, final String message) {
		ModelAndView result;

		result = new ModelAndView("security/edit");
		result.addObject(actorForm);
		result.addObject("isAdmin", isAdmin);
		result.addObject("message", message);

		return result;
	}
}
