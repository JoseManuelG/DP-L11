/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.BannerService;
import social.TwitterUtils;
import domain.Banner;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	// Services ------------------------------------------------------------------

	@Autowired
	private BannerService	bannerService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index() {
		final TwitterUtils twtutils = new TwitterUtils();
		List<Tweet> tweets;
		final List<TwitterProfile> followers;
		ModelAndView result;
		final SimpleDateFormat formatter;
		String moment;
		Banner banner;
		String requestURI;

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());
		banner = this.bannerService.randomBanner();
		tweets = twtutils.recentActivity("#acmechorbies");
		//		followers = twtutils.getFollowers("acmechorbies");
		requestURI = "";

		result = new ModelAndView("welcome/index");
		result.addObject("moment", moment);
		result.addObject("banner", banner);
		result.addObject("tweets", tweets);
		//		result.addObject("followers", followers);
		result.addObject("requestURI", requestURI);

		return result;
	}
}
