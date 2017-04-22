
package controllers.chorbi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.EventService;
import services.RegisterService;
import domain.Event;
import domain.Register;

@Controller
@RequestMapping("/event/chorbi")
public class RegisterChorbiController {

	// Services ---------------------------------------------------------------

	@Autowired
	private RegisterService	registerService;
	@Autowired
	private EventService	eventService;


	// Constructors -----------------------------------------------------------

	public RegisterChorbiController() {
		super();
	}

	// Methods -----------------------------------------------------------------		

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView received(final int eventId) {
		ModelAndView result;
		Register register;
		final Event event;
		event = this.eventService.findOne(eventId);
		register = this.registerService.create(event);
		/*
		 * try {
		 * this.searchTemplateService.save(res);
		 * result = this.search();
		 * 
		 * } catch (final IllegalArgumentException e) {
		 * results = new ArrayList<Chorbi>();
		 * 
		 * result = new ModelAndView("searchTemplate/chorbi/search.do");
		 * result.addObject("search", search);
		 * result.addObject("requestURI", "searchTemplate/chorbi/search.do");
		 * result.addObject("results", results);
		 * result.addObject("message", e.getMessage());
		 * }
		 */
		result = new ModelAndView("event/view.do?eventId=" + eventId);
		try {
			this.registerService.save(register);

		} catch (final Exception e) {
			result.addObject("Mensaje de Error", e.getMessage());

		}

		return result;
	}
}
