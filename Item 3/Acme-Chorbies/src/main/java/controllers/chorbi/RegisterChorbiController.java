
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
	public ModelAndView register(final int eventId) {
		ModelAndView result;
		Register register;
		final Event event;
		final Boolean siteFree, expired;

		event = this.eventService.findOne(eventId);
		register = this.registerService.create(event);

		result = new ModelAndView("event/view");
		try {
			this.registerService.save(register);
			result.addObject("registered", true);

		} catch (final Exception e) {
			result.addObject("message", e.getMessage());
			result.addObject("registered", false);

		}

		expired = this.eventService.checkExpired(event);
		siteFree = this.eventService.checkSiteFree(event);

		result.addObject("event", event);
		result.addObject("siteFree", siteFree);
		result.addObject("expired", expired);
		result.addObject("requestURI", "event/chorbi/view.do?eventId=" + eventId);
		return result;
	}
	@RequestMapping(value = "/unregister", method = RequestMethod.GET)
	public ModelAndView unregister(final int eventId) {
		ModelAndView result;
		final Event event;
		final Boolean siteFree, expired;

		event = this.eventService.findOne(eventId);

		result = new ModelAndView("event/view");
		try {
			this.registerService.delete(event);
			result.addObject("registered", false);

		} catch (final Exception e) {
			result.addObject("Mensaje de Error", e.getMessage());
			result.addObject("registered", true);
		}

		expired = this.eventService.checkExpired(event);
		siteFree = this.eventService.checkSiteFree(event);

		result.addObject("event", event);
		result.addObject("siteFree", siteFree);
		result.addObject("expired", expired);

		result.addObject("requestURI", "event/chorbi/view.do?eventId=" + eventId);
		return result;
	}
}
