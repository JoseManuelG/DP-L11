
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ChorbiRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Chorbi;
import domain.Coordinates;
import forms.ChorbiForm;

@Service
@Transactional
public class ChorbiService {

	//Managed Repository--------------------------------------------------------------------

	@Autowired
	private ChorbiRepository		chorbiRepository;

	// Supporting Services --------------------------------------

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private Validator				validator;

	@Autowired
	private ChirpService			chirpService;

	@Autowired
	private LikesService			likesService;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private SearchTemplateService	searchTemplateService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CoordinatesService		coordinatesService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private RegisterService			registerService;


	//Simple CRUD methods-------------------------------------------------------------------
	public Chorbi create() {
		Chorbi result;
		Authority authority;
		Coordinates coordinates;

		result = new Chorbi();
		this.customerService.setCustomerProperties(result);

		coordinates = this.coordinatesService.create();

		authority = new Authority();
		authority.setAuthority("CHORBI");

		result.getUserAccount().addAuthority(authority);
		result.setCoordinates(coordinates);

		return result;
	}

	public Chorbi save(final Chorbi chorbi) {
		Chorbi result;

		Assert.notNull(chorbi, "chorbi.error.null");
		Assert.isTrue(chorbi.getAge() >= 18, "chorbi.underage.error");

		chorbi.setUserAccount(this.userAccountService.save(chorbi.getUserAccount()));
		chorbi.setCoordinates(this.coordinatesService.save(chorbi.getCoordinates()));

		result = this.chorbiRepository.save(chorbi);
		Assert.notNull(result, "chorbi.error.commit");
		if (chorbi.getId() == 0)
			this.searchTemplateService.createForChorbi(result);

		return result;

	}
	public Chorbi findOne(final int id) {
		Chorbi result;
		result = this.chorbiRepository.findOne(id);
		return result;
	}

	public Collection<Chorbi> findAll() {
		Collection<Chorbi> result;
		result = this.chorbiRepository.findAll();
		return result;
	}

	public Long count() {
		return this.chorbiRepository.count();
	}

	public void delete() {
		Chorbi chorbi;
		chorbi = this.findChorbiByPrincipal();
		this.creditCardService.deleteFromChorbi(chorbi);
		this.searchTemplateService.deleteFromChorbi(chorbi);
		this.likesService.deleteFromChorbi(chorbi);
		this.chirpService.deleteFromChorbi(chorbi);
		this.registerService.deleteFromChorbi(chorbi);
		this.chorbiRepository.delete(chorbi);
		this.userAccountService.delete(chorbi.getUserAccount().getId());

	}
	public void flush() {
		this.chorbiRepository.flush();
	}

	public void updateChorbiesChargedFees() {

		List<Chorbi> chorbiesWithCC;
		Double fee;

		Assert.isTrue(this.actorService.findActorByPrincipal().getClass().equals(Administrator.class), "configuration.error.notadmin");

		chorbiesWithCC = new ArrayList<Chorbi>();
		chorbiesWithCC.addAll(this.chorbiRepository.getChorbiesWithCC());
		fee = this.configurationService.findConfiguration().getChorbiFee();

		for (final Chorbi c : chorbiesWithCC)
			c.setChargedFee(c.getChargedFee() + fee);

		this.chorbiRepository.save(chorbiesWithCC);

	}

	//Other Business methods-------------------------------------------------------------------

	public Chorbi findChorbiByPrincipal() {
		Chorbi result;
		result = this.chorbiRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return result;
	}

	public Chorbi reconstructNewChorbi(final ChorbiForm chorbiForm, final BindingResult binding) {
		Chorbi result;
		Md5PasswordEncoder encoder;

		result = this.create();
		encoder = new Md5PasswordEncoder();

		this.customerService.setReconstructNewCustomerProperties(result, chorbiForm);
		this.setReconstructProperties(result, chorbiForm);

		this.validator.validate(result, binding);
		result.getUserAccount().setPassword(encoder.encodePassword(chorbiForm.getUserAccount().getPassword(), null));
		return result;
	}

	public Chorbi reconstruct(final ChorbiForm chorbiForm, final Chorbi chorbi, final BindingResult binding) {
		Md5PasswordEncoder encoder;
		Chorbi result;

		result = this.create();
		encoder = new Md5PasswordEncoder();

		this.customerService.setReconstructCustomerProperties(result, chorbi, chorbiForm);
		this.setReconstructProperties(result, chorbiForm);

		this.validator.validate(result, binding);

		result.getUserAccount().setPassword(encoder.encodePassword(chorbiForm.getUserAccount().getPassword(), null));

		return result;
	}

	private void setReconstructProperties(final Chorbi result, final ChorbiForm chorbiForm) {
		Coordinates coordinates;

		coordinates = new Coordinates();

		result.setPicture(chorbiForm.getPicture());
		result.setDescription(chorbiForm.getDescription());
		result.setBirthDate(chorbiForm.getBirthDate());
		result.setGenre(chorbiForm.getGenre());
		result.setDesiredRelationship(chorbiForm.getDesiredRelationship());

		coordinates.setCity(chorbiForm.getCity());
		coordinates.setCountry(chorbiForm.getCountry());
		coordinates.setProvince(chorbiForm.getProvince());
		coordinates.setState(chorbiForm.getState());

		result.setCoordinates(coordinates);

	}

	public void banChorbi(final int chorbiId) {
		final List<Authority> auths = new ArrayList<Authority>(this.actorService.findActorByPrincipal().getUserAccount().getAuthorities());
		final Authority auth = auths.get(0);
		Assert.isTrue(auth.getAuthority().equals("ADMINISTRATOR"), "chorbi.error.notadmin");
		final UserAccount ua = this.chorbiRepository.findOne(chorbiId).getUserAccount();
		ua.setEnabled(false);
		this.userAccountService.save(ua);
	}

	public void unbanChorbi(final int chorbiId) {
		final List<Authority> auths = new ArrayList<Authority>(this.actorService.findActorByPrincipal().getUserAccount().getAuthorities());
		final Authority auth = auths.get(0);
		Assert.isTrue(auth.getAuthority().equals("ADMINISTRATOR"), "chorbi.error.notadmin");
		final UserAccount ua = this.chorbiRepository.findOne(chorbiId).getUserAccount();
		ua.setEnabled(true);
		this.userAccountService.save(ua);
	}

	@SuppressWarnings("deprecation")
	public Collection<Chorbi> searchChorbis(final String desiredRelathionship, final String genre, final String keyword, final String cityCoordinate, final String provinceCoordinate, final String countryCoordinate, final String stateCoordinate,
		final Integer age) {

		final Date aux = new Date(System.currentTimeMillis());
		final Date aux2 = new Date(aux.getYear() - age, aux.getMonth(), aux.getDay());
		final Date firstDate = new Date(aux2.getYear() - 5, aux.getMonth(), aux.getDay());
		final Date secondDate = new Date(aux2.getYear() + 5, aux.getMonth(), aux.getDay());

		final Collection<Chorbi> res = this.chorbiRepository.searchChorbis(desiredRelathionship, genre, keyword, cityCoordinate, provinceCoordinate, countryCoordinate, stateCoordinate, firstDate, secondDate);
		return res;
	}
	public Collection<Chorbi> searchChorbisWithoutAge(final String desiredRelathionship, final String genre, final String keyword, final String cityCoordinate, final String provinceCoordinate, final String countryCoordinate, final String stateCoordinate) {

		final Collection<Chorbi> res = this.chorbiRepository.searchChorbisWithoutAge(desiredRelathionship, genre, keyword, cityCoordinate, provinceCoordinate, countryCoordinate, stateCoordinate);
		return res;
	}
	public Collection<Chorbi> searchChorbisWithoutAgeAndGenre(final String desiredRelathionship, final String keyword, final String cityCoordinate, final String provinceCoordinate, final String countryCoordinate, final String stateCoordinate) {
		Collection<Chorbi> result;
		result = this.chorbiRepository.searchChorbisWithoutAgeAndGenre(desiredRelathionship, keyword, cityCoordinate, provinceCoordinate, countryCoordinate, stateCoordinate);
		return result;
	}

	@SuppressWarnings("deprecation")
	public Collection<Chorbi> searchChorbisWithOutGenre(final String desiredRelathionship, final String keyword, final String cityCoordinate, final String provinceCoordinate, final String countryCoordinate, final String stateCoordinate, final Integer age) {
		final Date aux = new Date(System.currentTimeMillis());
		Collection<Chorbi> result;
		final Date aux2 = new Date(aux.getYear() - age, aux.getMonth(), aux.getDay());
		final Date firstDate = new Date(aux2.getYear() - 5, aux.getMonth(), aux.getDay());
		final Date secondDate = new Date(aux2.getYear() + 5, aux.getMonth(), aux.getDay());

		result = this.chorbiRepository.searchChorbisWithOutGenre(desiredRelathionship, keyword, cityCoordinate, provinceCoordinate, countryCoordinate, stateCoordinate, firstDate, secondDate);
		return result;
	}
	public Coordinates findCoordinatesByUserAccount() {
		final Chorbi chorbi = this.findChorbiByPrincipal();
		Coordinates coordinates;

		coordinates = this.chorbiRepository.findCoordinatesByUserAccountId(chorbi.getId());
		return coordinates;
	}

	public Boolean validLike(final Chorbi chorbi) {
		Boolean res;
		Actor principal;

		principal = this.actorService.findActorByPrincipal();
		res = !principal.equals(chorbi) && this.likesService.findUniqueLike(chorbi.getId());

		return res;
	}

}
