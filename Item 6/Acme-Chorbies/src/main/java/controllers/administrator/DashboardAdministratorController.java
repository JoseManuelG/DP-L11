
package controllers.administrator;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.DashboardService;
import controllers.AbstractController;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	@Autowired
	private DashboardService	dashboardService;


	// Constructors -----------------------------------------------------------
	public DashboardAdministratorController() {
		super();
	}


	private static DecimalFormat	df2	= new DecimalFormat("0.##");


	// List --------------------------------------------------------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;
		result = new ModelAndView("dashboard/administrator/dashboard");

		result.addObject("requestURI", "dashboard/administrator/dashboard.do");

		//Queries ChorbiService

		result.addObject("chorbiesGroupedByCity", this.dashboardService.numberOfChorbiesPerCity());
		result.addObject("chorbiesGroupedByCountry", this.dashboardService.numberOfChorbiesPerCountry());
		result.addObject("minumumChorbiAge", this.dashboardService.minAgeOfChorbies());
		result.addObject("maximumChorbiAge", this.dashboardService.maxAgeOfChorbies());
		result.addObject("averageChorbiAge", DashboardAdministratorController.df2.format(this.dashboardService.averageAgeOfChorbies()));
		result.addObject("ratioOfNoCCAndInvalidCCVersusValidCC", DashboardAdministratorController.df2.format(this.dashboardService.ratioChorbiesWithoutValidCreditCard()));

		//Queries SearchService

		result.addObject("ratioActivitiesSearch", DashboardAdministratorController.df2.format(this.dashboardService.ratioChorbiesWhoDesireActivities()));
		result.addObject("ratioFriendshipSearch", DashboardAdministratorController.df2.format(this.dashboardService.ratioChorbiesWhoDesireFriendship()));
		result.addObject("ratioLoveSearch", DashboardAdministratorController.df2.format(this.dashboardService.ratioChorbiesWhoDesireLove()));

		//Queries LikeService

		result.addObject("chorbiesOrderedByLikes", this.dashboardService.chorbiesOrderedByLikes());
		result.addObject("minimumChorbiLikes", this.dashboardService.minNumberOfLikesPerChorbi());
		result.addObject("maximumChorbiLikes", this.dashboardService.maxNumberOfLikesPerChorbi());
		result.addObject("averageChorbiLikes", DashboardAdministratorController.df2.format(this.dashboardService.avgNumberOfLikesPerChorbi()));

		//Queries ChirpService

		result.addObject("minimumChirpsReceivedByChorbi", this.dashboardService.minOfChirpsReceivedPerChorbi());
		result.addObject("maximumChirpsReceivedByChorbi", this.dashboardService.maxOfChirpsReceivedPerChorbi());
		result.addObject("averageChirpsReceivedByChorbi", DashboardAdministratorController.df2.format(this.dashboardService.averageOfChirpsReceivedPerChorbi()));
		result.addObject("minimumChirpsSentByChorbi", this.dashboardService.minOfChirpsSentPerChorbi());
		result.addObject("maximumChirpsSentByChorbi", this.dashboardService.maxOfChirpsSentPerChorbi());
		result.addObject("averageChirpsSentByChorbi", DashboardAdministratorController.df2.format(this.dashboardService.averageOfChirpsSentPerChorbi()));
		result.addObject("ChorbiesWithMoreReceivedChirps", this.dashboardService.findChorbiesWhoGotMoreChirps());
		result.addObject("ChorbiesWithMoreSentChirps", this.dashboardService.findChorbiesWhoSentMoreChirps());

		//Queries Chorbies 2.0

		result.addObject("getManagersOrderedByEvents", this.dashboardService.getManagersOrderedByEvents());
		result.addObject("getManagersWithChargedFee", this.dashboardService.getManagersWithChargedFee());
		result.addObject("getChorbiesOrderedByEvents", this.dashboardService.getChorbiesOrderedByEvents());
		result.addObject("getChorbiesWithChargedFee", this.dashboardService.getChorbiesWithChargedFee());
		result.addObject("getChorbiesWithMinMaxAvgStars", this.dashboardService.getChorbiesWithMinMaxAvgStars());
		result.addObject("getChorbiesWithAvgStarsOrderedByAvgStars", this.dashboardService.getChorbiesWithAvgStarsOrderedByAvgStars());

		return result;
	}

}
