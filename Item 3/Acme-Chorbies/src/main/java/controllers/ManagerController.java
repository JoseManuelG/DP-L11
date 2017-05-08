
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ManagerService;
import domain.Manager;
import forms.ManagerForm;

@Controller
@RequestMapping("/managers")
public class ManagerController extends AbstractController {

	//Services------------------------------------------------------------
	@Autowired
	private ManagerService	managerService;


	//Register ------------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView createManager() {
		ModelAndView result;
		ManagerForm managerForm;

		managerForm = new ManagerForm();
		result = this.createRegisterModelAndView(managerForm);

		return result;
	}

	// Save ---------------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ManagerForm managerForm, final BindingResult binding) {
		ModelAndView result;
		Manager manager;

		manager = this.managerService.reconstructNewManager(managerForm, binding);

		if (binding.hasErrors())
			result = this.createRegisterModelAndView(managerForm);
		else
			try {
				this.managerService.save(manager);
				result = new ModelAndView("redirect:/");
			} catch (final IllegalArgumentException oops) {
				result = this.createRegisterModelAndView(managerForm, oops.getMessage());
			}

		return result;
	}

	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Manager manager;
		ManagerForm managerForm;

		manager = this.managerService.findManagerByPrincipal();

		managerForm = new ManagerForm(manager);

		result = this.createEditModelAndView(managerForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final ManagerForm managerForm, final BindingResult binding) {
		ModelAndView result;
		Manager principal, managerResult;

		principal = this.managerService.findManagerByPrincipal();

		managerResult = this.managerService.reconstruct(managerForm, principal, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(managerForm);
		else
			try {
				this.managerService.save(managerResult);
				result = new ModelAndView("redirect:/managers/manager/myProfile.do");
			} catch (final IllegalArgumentException oops) {
				result = this.createEditModelAndView(managerForm, oops.getMessage());
			}

		return result;
	}

	// Delete ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final ManagerForm managerForm) {
		ModelAndView result;

		try {
			this.managerService.delete();
			result = new ModelAndView("redirect:/j_spring_security_logout");

		} catch (final IllegalArgumentException e) {
			result = this.createEditModelAndView(managerForm, e.getMessage());
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createRegisterModelAndView(final ManagerForm managerForm) {
		ModelAndView result;

		result = this.createRegisterModelAndView(managerForm, null);

		return result;
	}

	protected ModelAndView createRegisterModelAndView(final ManagerForm managerForm, final String message) {
		ModelAndView result;
		String requestURI;

		requestURI = "manager/register.do";

		result = new ModelAndView("manager/register");
		result.addObject("managerForm", managerForm);
		result.addObject("isNew", true);
		result.addObject("requestURI", requestURI);
		result.addObject("message", message);

		return result;
	}
	protected ModelAndView createEditModelAndView(final ManagerForm managerForm) {
		ModelAndView result;

		result = this.createEditModelAndView(managerForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ManagerForm managerForm, final String message) {
		ModelAndView result;
		String requestURI;

		requestURI = "manager/edit.do";

		result = new ModelAndView("manager/edit");
		result.addObject("managerForm", managerForm);
		result.addObject("isNew", false);
		result.addObject("requestURI", requestURI);
		result.addObject("message", message);

		return result;
	}
}
