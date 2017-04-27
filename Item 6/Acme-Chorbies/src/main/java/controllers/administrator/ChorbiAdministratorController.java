
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import controllers.AbstractController;
import domain.Chorbi;

@Controller
@RequestMapping("/chorbi/administrator")
public class ChorbiAdministratorController extends AbstractController {

	@Autowired
	private ChorbiService	chorbiService;


	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Chorbi> chorbies;

		chorbies = this.chorbiService.findAll();

		result = new ModelAndView("chorbi/administrator/list");
		result.addObject("chorbies", chorbies);
		result.addObject("requestURI", "chorbi/administrator/list.do");

		return result;
	}

	// Constructors -----------------------------------------------------------

	public ChorbiAdministratorController() {
		super();
	}

	// Ban 	-------------------------------------------------------------------

	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam final int chorbiId) {
		ModelAndView result;
		//volver a poner lo comentado cuando haya un view
		//Chorbi chorbi;

		//chorbi = this.chorbiService.findOne(chorbiId);

		this.chorbiService.banChorbi(chorbiId);

		//result = new ModelAndView("redirect:/chorbi/view.do?chorbiId=" + chorbi.getId()); 
		result = new ModelAndView("redirect:/chorbi/administrator/list.do");

		return result;
	}

	// Unban 	-------------------------------------------------------------------

	@RequestMapping(value = "/unban", method = RequestMethod.GET)
	public ModelAndView unban(@RequestParam final int chorbiId) {
		ModelAndView result;
		//volver a poner lo comentado cuando haya un view
		//Chorbi chorbi;

		//chorbi = this.chorbiService.findOne(chorbiId);

		this.chorbiService.unbanChorbi(chorbiId);
		//result = new ModelAndView("redirect:/chorbi/view.do?chorbiId=" + chorbi.getId());
		result = new ModelAndView("redirect:/chorbi/administrator/list.do");
		return result;
	}

	// updateChorbiesChargedFees 	-------------------------------------------------------------------

	@RequestMapping(value = "/updateChorbiesChargedFees", method = RequestMethod.GET)
	public ModelAndView updateChorbiesChargedFees() {
		ModelAndView result;

		this.chorbiService.updateChorbiesChargedFees();

		result = new ModelAndView("redirect:/chorbi/administrator/successFee.do");

		return result;
	}

	@RequestMapping(value = "/successFee", method = RequestMethod.GET)
	public ModelAndView successFee() {
		ModelAndView result;

		result = new ModelAndView("chorbi/administrator/updateChorbiesChargedFees");
		result.addObject("requestURI", "chorbi/administrator/successFee.do");

		return result;
	}

}
