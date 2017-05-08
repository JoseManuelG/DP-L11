
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ManagerService;
import controllers.AbstractController;
import domain.Manager;

@Controller
@RequestMapping("/managers/administrator")
public class ManagerAdministratorController extends AbstractController {

	@Autowired
	private ManagerService	managerService;


	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Manager> managers;

		managers = this.managerService.findAll();

		result = new ModelAndView("manager/administrator/list");
		result.addObject("managers", managers);
		result.addObject("requestURI", "managers/administrator/list.do");

		return result;
	}

	// Constructors -----------------------------------------------------------

	public ManagerAdministratorController() {
		super();
	}

}
