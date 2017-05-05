
package controllers.chorbi;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SearchTemplateService;
import controllers.AbstractController;
import domain.Chorbi;
import domain.SearchTemplate;

@Controller
@RequestMapping("/searchTemplate/chorbi")
public class SearchTemplateChorbiController extends AbstractController {

	@Autowired
	private SearchTemplateService	searchTemplateService;


	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {

		ModelAndView result;
		final Collection<Chorbi> results;
		SearchTemplate search;

		results = this.searchTemplateService.getValidResultsByPrincipal();
		search = this.searchTemplateService.findByPrincipal();
		if (search.getAge().equals(0))
			search.setAge(null);

		result = new ModelAndView("searchTemplate/chorbi/search.do");
		result.addObject("results", results);
		result.addObject("search", search);
		result.addObject("requestURI", "searchTemplate/chorbi/search.do");

		return result;
	}
	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "save")
	public ModelAndView search(final SearchTemplate search, final BindingResult binding) {
		ModelAndView result;
		SearchTemplate res;

		res = this.searchTemplateService.reconstruct(search, binding);
		Collection<Chorbi> results;
		if (binding.hasErrors()) {
			results = new ArrayList<Chorbi>();

			result = new ModelAndView("searchTemplate/chorbi/search.do");
			result.addObject("search", search);
			result.addObject("requestURI", "searchTemplate/chorbi/search.do");
			result.addObject("results", results);

		} else
			try {
				this.searchTemplateService.save(res);
				result = this.search();

			} catch (final IllegalArgumentException e) {
				results = new ArrayList<Chorbi>();

				result = new ModelAndView("searchTemplate/chorbi/search.do");
				result.addObject("search", search);
				result.addObject("requestURI", "searchTemplate/chorbi/search.do");
				result.addObject("results", results);
				result.addObject("message", e.getMessage());
			}
		return result;
	}
}
