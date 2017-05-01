
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import domain.Chorbi;
import forms.ChorbiForm;

@Controller
@RequestMapping("/chorbi")
public class ChorbiController extends AbstractController {

	//Services------------------------------------------------------------
	@Autowired
	private ChorbiService	chorbiService;


	//Register ------------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		ChorbiForm chorbiForm;

		chorbiForm = new ChorbiForm();
		result = this.createRegisterModelAndView(chorbiForm);

		return result;
	}

	// Save ---------------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ChorbiForm chorbiForm, final BindingResult binding) {
		ModelAndView result;
		Chorbi chorbi;

		chorbi = this.chorbiService.reconstructNewChorbi(chorbiForm, binding);

		if (binding.hasErrors())
			result = this.createRegisterModelAndView(chorbiForm);
		else
			try {
				this.chorbiService.save(chorbi);
				result = new ModelAndView("redirect:/");
			} catch (final IllegalArgumentException oops) {
				result = this.createRegisterModelAndView(chorbiForm, oops.getMessage());
			}

		return result;
	}

	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Chorbi chorbi;
		ChorbiForm chorbiForm;

		chorbi = this.chorbiService.findChorbiByPrincipal();

		chorbiForm = new ChorbiForm(chorbi);

		result = this.createEditModelAndView(chorbiForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final ChorbiForm chorbiForm, final BindingResult binding) {
		ModelAndView result;
		Chorbi principal, chorbiResult;

		principal = this.chorbiService.findChorbiByPrincipal();

		chorbiResult = this.chorbiService.reconstruct(chorbiForm, principal, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(chorbiForm);
		else
			try {
				this.chorbiService.save(chorbiResult);
				result = new ModelAndView("redirect:/chorbi/chorbi/myProfile.do");
			} catch (final IllegalArgumentException oops) {
				result = this.createEditModelAndView(chorbiForm, oops.getMessage());
			}

		return result;
	}

	// Delete ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final ChorbiForm chorbiForm) {
		ModelAndView result;

		try {
			this.chorbiService.delete();
			result = new ModelAndView("redirect:/j_spring_security_logout");

		} catch (final IllegalArgumentException e) {
			result = this.createEditModelAndView(chorbiForm, e.getMessage());
		}

		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createRegisterModelAndView(final ChorbiForm chorbiForm) {
		ModelAndView result;

		result = this.createRegisterModelAndView(chorbiForm, null);

		return result;
	}

	protected ModelAndView createRegisterModelAndView(final ChorbiForm chorbiForm, final String message) {
		ModelAndView result;
		String requestURI;

		requestURI = "chorbi/register.do";

		result = new ModelAndView("chorbi/register");
		result.addObject("chorbiForm", chorbiForm);
		result.addObject("isNew", true);
		result.addObject("requestURI", requestURI);
		result.addObject("message", message);

		return result;
	}
	protected ModelAndView createEditModelAndView(final ChorbiForm chorbiForm) {
		ModelAndView result;

		result = this.createEditModelAndView(chorbiForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ChorbiForm chorbiForm, final String message) {
		ModelAndView result;
		String requestURI;

		requestURI = "chorbi/edit.do";

		result = new ModelAndView("chorbi/edit");
		result.addObject("chorbiForm", chorbiForm);
		result.addObject("isNew", false);
		result.addObject("requestURI", requestURI);
		result.addObject("message", message);

		return result;
	}
}
