
package controllers.customer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.CreditCardService;
import controllers.AbstractController;
import domain.CreditCard;

@Controller
@RequestMapping("/creditCard/customer")
public class CreditCardCustomerController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private CreditCardService	creditCardService;


	// Constructors -----------------------------------------------------------

	public CreditCardCustomerController() {
		super();
	}

	// Create --------------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CreditCard creditCard;

		creditCard = this.creditCardService.create();
		result = this.createEditModelAndView(creditCard);

		return result;

	}
	// View ---------------------------------------------------------------

	@RequestMapping(value = "/myCreditCard", method = RequestMethod.GET)
	public ModelAndView view() {
		final ModelAndView result;
		CreditCard creditCard;

		creditCard = this.creditCardService.getCreditCardByPrincipal();
		result = new ModelAndView("creditCard/customer/myCreditCard");

		if (creditCard != null)
			result.addObject("creditCard", creditCard);
		else
			result.addObject("editable", Boolean.FALSE);
		return result;
	}
	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		CreditCard creditCard;

		creditCard = this.creditCardService.getCreditCardByPrincipal();
		result = this.createEditModelAndView(creditCard);

		return result;
	}

	// Save ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody
	ModelAndView save(@Valid final CreditCard creditCard, final BindingResult binding) {
		ModelAndView result;
		CreditCard cardResult;

		cardResult = this.creditCardService.reconstruct(creditCard, binding);
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.createEditModelAndView(creditCard);
		} else
			try {
				this.creditCardService.save(cardResult);
				result = new ModelAndView("redirect:../customer/myCreditCard.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(creditCard, "creditCard.commit.error");
			}

		return result;
	}

	// Delete ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final CreditCard creditCard, final BindingResult binding) {
		ModelAndView result;
		CreditCard cardResult;

		cardResult = this.creditCardService.reconstruct(creditCard, binding);
		try {
			this.creditCardService.delete(cardResult);
			result = new ModelAndView("redirect:../customer/myCreditCard.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(creditCard, "creditCard.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final CreditCard creditCard) {
		ModelAndView result;

		result = this.createEditModelAndView(creditCard, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CreditCard creditCard, final String message) {
		ModelAndView result;

		result = new ModelAndView("creditCard/customer/edit");
		result.addObject("creditCard", creditCard);
		result.addObject("message", message);
		return result;
	}

}
