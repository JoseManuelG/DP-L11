
package controllers.chorbi;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import services.LikesService;
import controllers.AbstractController;
import domain.Chorbi;
import domain.Likes;

@Controller
@RequestMapping("/likes")
public class LikesChorbiController extends AbstractController {

	@Autowired
	private LikesService	likesService;

	@Autowired
	private ChorbiService	chorbiService;


	// List --------------------------------------------------------------------

	// Create --------------------------------------------------------------------
	@RequestMapping(value = "/chorbi/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int chorbiId) {
		Chorbi liked;
		ModelAndView result;
		Likes likes;
		String requestURI;

		liked = this.chorbiService.findOne(chorbiId);

		likes = this.likesService.create(liked);

		result = this.createEditModelAndView(likes);

		requestURI = "chorbi/chorbi/view.do?chorbiId=" + likes.getLiked().getId();

		result.addObject("requestURI", requestURI);
		result.addObject("likes", likes);
		return result;

	}

	// Save ---------------------------------------------------------------
	@RequestMapping(value = "/chorbi/create", method = RequestMethod.POST, params = "save")
	public @ResponseBody
	ModelAndView save(final Likes likes, final BindingResult binding) {
		ModelAndView result;
		Likes likesReconstructed;

		likesReconstructed = this.likesService.reconstruct(likes, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(likes);
		else
			try {

				this.likesService.save(likesReconstructed);
				result = new ModelAndView("redirect:sent.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(likes, oops.getMessage());
			}

		return result;
	}

	// View ---------------------------------------------------------------

	@RequestMapping(value = "/chorbi/sent", method = RequestMethod.GET)
	public ModelAndView sent() {
		ModelAndView result;
		Collection<Likes> res;

		res = this.likesService.findSentLikesOfPrincipal();

		result = new ModelAndView("likes/list");
		result.addObject("likes", res);
		result.addObject("requestURI", "likes/chorbi/sent.do");

		return result;
	}

	@RequestMapping(value = "/chorbi/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam final int likesId) {
		ModelAndView result;
		Likes likes;
		Chorbi chorbi;
		Boolean aux;

		likes = this.likesService.findOne(likesId);
		chorbi = this.chorbiService.findChorbiByPrincipal();

		aux = likes.getLiker().equals(chorbi);

		result = new ModelAndView("likes/chorbi/view");
		result.addObject("likes", likes);
		result.addObject("aux", aux);

		result.addObject("requestURI", "likes/chorbi/view.do?likesId=" + likesId);

		return result;
	}

	// Delete ---------------------------------------------------------------
	@RequestMapping(value = "/chorbi/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int likesId) {
		ModelAndView result;
		Likes likes;
		Collection<Likes> res;

		likes = this.likesService.findOne(likesId);

		this.likesService.delete(likes);
		res = this.likesService.findSentLikesOfPrincipal();

		result = new ModelAndView("likes/list");
		result.addObject("likes", res);
		result.addObject("requestURI", "likes/chorbi/sent.do");

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Likes likes) {
		ModelAndView result;

		result = this.createEditModelAndView(likes, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Likes likes, final String message) {
		ModelAndView result;
		result = new ModelAndView("likes/chorbi/create");

		result.addObject("likes", likes);
		result.addObject("message", message);
		return result;
	}

}
