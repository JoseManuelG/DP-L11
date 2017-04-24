
package controllers.chorbi;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ChorbiService;
import services.LikesService;
import controllers.AbstractController;
import domain.Actor;
import domain.Chorbi;
import domain.Likes;

@Controller
@RequestMapping("/chorbi")
public class ViewChorbiController extends AbstractController {

	@Autowired
	private LikesService	likesService;

	@Autowired
	private ChorbiService	chorbiService;

	@Autowired
	private ActorService	actorService;


	// List --------------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Chorbi> chorbies;

		chorbies = this.chorbiService.findAll();

		result = new ModelAndView("chorbi/list");
		result.addObject("chorbies", chorbies);
		result.addObject("requestURI", "chorbi/list.do");

		return result;
	}

	@RequestMapping(value = "/recivedLikedlist", method = RequestMethod.GET)
	public ModelAndView recivedLikedList() {
		ModelAndView result;
		Collection<Likes> likes;

		likes = this.likesService.findReceivedLikesOfChorbi(this.chorbiService.findChorbiByPrincipal().getId());

		result = new ModelAndView("chorbi/recivedLikedlist");

		result.addObject("requestURI", "chorbi/recivedLikedlist");
		result.addObject("likes", likes);
		return result;
	}

	@RequestMapping(value = "/chorbi/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam final int chorbiId) {
		ModelAndView result;
		Chorbi chorbi;
		Actor principal;
		Boolean aux, myPrincipal;
		Collection<Likes> likes;

		principal = this.actorService.findActorByPrincipal();
		chorbi = this.chorbiService.findOne(chorbiId);
		aux = this.chorbiService.validLike(chorbi);
		myPrincipal = chorbi.equals(principal);
		likes = this.likesService.findReceivedLikesOfChorbi(chorbiId);

		result = new ModelAndView("chorbi/chorbi/view");
		result.addObject("chorbi", chorbi);
		result.addObject("aux", aux);
		result.addObject("myPrincipal", myPrincipal);
		result.addObject("likes", likes);

		result.addObject("requestURI", "chorbi/chorbi/view.do?chorbiId=" + chorbiId);

		return result;
	}
	@RequestMapping(value = "/chorbi/myProfile", method = RequestMethod.GET)
	public ModelAndView myProfile() {
		ModelAndView result;
		Chorbi principal;

		principal = this.chorbiService.findChorbiByPrincipal();

		result = this.view(principal.getId());
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Chorbi chorbi) {
		ModelAndView result;

		result = this.createEditModelAndView(chorbi, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Chorbi chorbi, final String message) {
		ModelAndView result;
		result = new ModelAndView("chorbi/chorbi/create");

		result.addObject("chorbi", chorbi);
		result.addObject("message", message);
		return result;
	}

}
