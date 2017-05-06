
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SearchTemplateRepository;
import domain.Chorbi;
import domain.Coordinates;
import domain.SearchTemplate;

@Service
@Transactional
public class SearchTemplateService {

	// Managed Repository --------------------------------------
	@Autowired
	private SearchTemplateRepository	searchTemplateRepository;

	// Supporting Services --------------------------------------
	@Autowired
	private ActorService				actorService;
	@Autowired
	private ChorbiService				chorbiService;
	@Autowired
	private ConfigurationService		configurationService;
	@Autowired
	private CoordinatesService			coordinatesService;
	@Autowired
	private CreditCardService			creditCardService;
	@Autowired
	private Validator					validator;


	// Simple CRUD methods --------------------------------------
	public SearchTemplate create() {
		SearchTemplate result;
		Coordinates coordinates;

		result = new SearchTemplate();
		result.setAge(0);
		result.setCacheMoment(new Date(System.currentTimeMillis() - this.configurationService.findConfiguration().getCachedTime()));
		result.setChorbies(new ArrayList<Chorbi>());
		result.setDesiredRelationship("");
		result.setGenre("");
		result.setKeyword("");
		coordinates = this.coordinatesService.create();
		result.setCoordinates(coordinates);

		return result;
	}

	public SearchTemplate findOne(final int searchTemplateId) {
		SearchTemplate result;

		result = this.searchTemplateRepository.findOne(searchTemplateId);

		return result;
	}

	public SearchTemplate save(final SearchTemplate searchTemplate) {
		SearchTemplate result;
		final Collection<Chorbi> chorbies;
		Date timeOfCache, lastSearch;

		//Revisar que el search guardado sea del Principal
		Assert.isTrue(this.actorService.findActorByPrincipal().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("CHORBI"));
		Assert.isTrue(this.chorbiService.findChorbiByPrincipal().equals(searchTemplate.getChorbi()));
		Assert.notNull(this.creditCardService.getCreditCardByPrincipal(), "search.notCreditCard");
		Assert.isTrue(this.creditCardService.checkCreditCardByPrincipal(), "search.not.valid.credit.card");
		//Fechas para comprobar el tiempo de caché
		timeOfCache = new Date(System.currentTimeMillis() - this.configurationService.findConfiguration().getCachedTime());
		lastSearch = new Date(searchTemplate.getCacheMoment().getTime());

		result = searchTemplate;

		//Comprobamos si el SearchTemplate ha sido modificado
		//TODO Probar a pasar el bloque if al FindOne() para que saliera actualizado automaticamente
		if (lastSearch.before(timeOfCache) || this.searchTemplateHasBeenModified(searchTemplate)) {

			result.setCacheMoment(new Date(System.currentTimeMillis() - 1000));

			//Busqueda en base de Datos
			if (!searchTemplate.getAge().equals(0) && !searchTemplate.getGenre().isEmpty())

				chorbies = this.chorbiService.searchChorbis(searchTemplate.getDesiredRelationship(), searchTemplate.getGenre(),

				searchTemplate.getKeyword(), searchTemplate.getCoordinates().getCity(), searchTemplate.getCoordinates().getProvince(),

				searchTemplate.getCoordinates().getCountry(), searchTemplate.getCoordinates().getState(), searchTemplate.getAge());

			else if (searchTemplate.getAge().equals(0) && !searchTemplate.getGenre().isEmpty())

				chorbies = this.chorbiService.searchChorbisWithoutAge(searchTemplate.getDesiredRelationship(), searchTemplate.getGenre(),

				searchTemplate.getKeyword(), searchTemplate.getCoordinates().getCity(), searchTemplate.getCoordinates().getProvince(),

				searchTemplate.getCoordinates().getCountry(), searchTemplate.getCoordinates().getState());

			else if (!searchTemplate.getAge().equals(0) && searchTemplate.getGenre().isEmpty())

				chorbies = this.chorbiService.searchChorbisWithOutGenre(searchTemplate.getDesiredRelationship(), searchTemplate.getKeyword(), searchTemplate.getCoordinates().getCity(), searchTemplate.getCoordinates().getProvince(),

				searchTemplate.getCoordinates().getCountry(), searchTemplate.getCoordinates().getState(), searchTemplate.getAge());
			else
				chorbies = this.chorbiService.searchChorbisWithoutAgeAndGenre(searchTemplate.getDesiredRelationship(),

				searchTemplate.getKeyword(), searchTemplate.getCoordinates().getCity(), searchTemplate.getCoordinates().getProvince(),

				searchTemplate.getCoordinates().getCountry(), searchTemplate.getCoordinates().getState());

			result.setChorbies(chorbies);

			result = this.searchTemplateRepository.save(result);
		}

		Assert.notNull(result);

		return result;
	}
	//Comprueba si ha sido modificado el searchTemplate
	private boolean searchTemplateHasBeenModified(final SearchTemplate searchTemplate) {
		SearchTemplate old;
		boolean result;
		old = this.searchTemplateRepository.findOne(searchTemplate.getId());
		result = !searchTemplate.getAge().equals(old.getAge())

		|| !(searchTemplate.getDesiredRelationship().equals(old.getDesiredRelationship()))

		|| !(searchTemplate.getGenre().equals(old.getGenre()))

		|| !(searchTemplate.getKeyword().equals(old.getKeyword()))

		|| !(searchTemplate.getCoordinates().getCity().equals(old.getCoordinates().getCity()))

		|| !(searchTemplate.getCoordinates().getCountry().equals(old.getCoordinates().getCountry()))

		|| !(searchTemplate.getCoordinates().getProvince().equals(old.getCoordinates().getProvince()))

		|| !(searchTemplate.getCoordinates().getState().equals(old.getCoordinates().getState()));

		return result;

	}

	public void deleteFromChorbi(final Chorbi chorbi) {
		SearchTemplate searchTemplate;
		Collection<SearchTemplate> searchTemplates;

		searchTemplate = this.findByChorbi(chorbi.getId());

		this.coordinatesService.delete(searchTemplate.getCoordinates());

		this.searchTemplateRepository.delete(searchTemplate);

		searchTemplates = this.searchTemplateRepository.findAllWithChorbi(chorbi.getId());
		for (final SearchTemplate template : searchTemplates)
			template.getChorbies().remove(chorbi);

		this.searchTemplateRepository.save(searchTemplates);
	}
	// Other business methods --------------------------------------

	public SearchTemplate findByPrincipal() {
		final SearchTemplate result;
		final int actorId = this.actorService.findActorByPrincipal().getId();
		result = this.findByChorbi(actorId);
		return result;
	}

	public SearchTemplate findByChorbi(final int chorbiId) {
		final SearchTemplate result;

		result = this.searchTemplateRepository.findByChrobi(chorbiId);

		return result;
	}

	public SearchTemplate reconstruct(final SearchTemplate searchTemplate, final BindingResult binding) {
		SearchTemplate res, old;
		Integer auxAge;
		String auxGender;
		String auxDesiredRelationship;

		old = this.findOne(searchTemplate.getId());
		res = this.create();

		//Valores genericos por si el usuario no quiere especificar estos datos-->
		auxAge = 0;
		auxGender = "";
		auxDesiredRelationship = "";
		if (searchTemplate.getAge() != null)
			auxAge = searchTemplate.getAge();

		if (!searchTemplate.getDesiredRelationship().equals("all"))
			auxDesiredRelationship = searchTemplate.getDesiredRelationship();

		if (!searchTemplate.getGenre().equals("all"))
			auxGender = searchTemplate.getGenre();
		//<--

		//old things
		res.setId(old.getId());
		res.setVersion(old.getVersion());
		res.setCacheMoment(old.getCacheMoment());
		res.setChorbies(old.getChorbies());
		res.setChorbi(old.getChorbi());

		//Coordinates
		final Coordinates aux;
		aux = new Coordinates();
		aux.setId(old.getCoordinates().getId());
		aux.setVersion(old.getCoordinates().getVersion());
		aux.setCity(searchTemplate.getCoordinates().getCity());
		aux.setCountry(searchTemplate.getCoordinates().getCountry());
		aux.setProvince(searchTemplate.getCoordinates().getProvince());
		aux.setState(searchTemplate.getCoordinates().getState());

		res.setCoordinates(aux);
		//New things
		res.setDesiredRelationship(auxDesiredRelationship);
		res.setAge(auxAge);
		res.setGenre(auxGender);
		res.setKeyword(searchTemplate.getKeyword());
		this.validator.validate(res, binding);

		return res;
	}

	public void createForChorbi(final Chorbi result) {
		SearchTemplate searchTemplate;
		searchTemplate = this.create();
		searchTemplate.setChorbi(result);
		this.searchTemplateRepository.save(searchTemplate);
	}

	public SearchTemplate findByPrincipalToShow() {
		SearchTemplate search;

		search = this.findByPrincipal();

		//	if (search.getAge().equals(0))
		//	search.setAge(null);

		return search;
	}

	public Collection<Chorbi> getValidResultsByPrincipal() {
		Date timeOfCache, lastSearch;
		Collection<Chorbi> results;
		SearchTemplate search;
		search = this.findByPrincipal();
		//revisa tiempo de cacheo
		lastSearch = new Date(search.getCacheMoment().getTime());
		timeOfCache = new Date(System.currentTimeMillis() - this.configurationService.findConfiguration().getCachedTime());

		if (lastSearch.after(timeOfCache))
			results = search.getChorbies();
		else
			results = new ArrayList<Chorbi>();

		return results;
	}

	public void flush() {
		this.searchTemplateRepository.flush();

	}
}
