
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
import domain.Chorbi;
import domain.Coordinates;
import forms.ActorForm;

@Service
@Transactional
public class ChorbiService {

	//Managed Repository--------------------------------------------------------------------

	@Autowired
	private ChorbiRepository		chorbiRepository;

	// Supporting Services --------------------------------------

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


	//Simple CRUD methods-------------------------------------------------------------------
	public Chorbi create() {
		final Chorbi result = new Chorbi();
		return result;
	}

	public Chorbi save(final Chorbi chorbi) {
		Chorbi result;

		Assert.notNull(chorbi, "chorbi.error.null");
		Assert.isTrue(chorbi.getAge() >= 18, "chorbi.underage.error");
		chorbi.setUserAccount(this.userAccountService.save(chorbi.getUserAccount()));
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
		this.chorbiRepository.delete(chorbi);
		this.userAccountService.delete(chorbi.getUserAccount().getId());

	}
	public void flush() {
		this.chorbiRepository.flush();
	}
	//Other Business methods-------------------------------------------------------------------

	public Chorbi findChorbiByPrincipal() {
		Chorbi result;
		result = this.chorbiRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return result;
	}

	public Chorbi reconstruct(final ActorForm actorForm, final BindingResult binding) {
		final Chorbi result = this.create();
		Coordinates coordinates, finalCoordinates;

		coordinates = new Coordinates();

		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final UserAccount userAccount = new UserAccount();
		userAccount.setUsername(actorForm.getUserAccount().getUsername());
		userAccount.setPassword(actorForm.getUserAccount().getPassword());
		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority authority = new Authority();
		authority.setAuthority("CHORBI");
		authorities.add(authority);
		userAccount.setEnabled(true);
		userAccount.setAuthorities(authorities);

		result.setName(actorForm.getName());
		result.setSurname(actorForm.getSurname());
		result.setEmail(actorForm.getEmail());
		result.setPhone(actorForm.getPhone());
		result.setPicture(actorForm.getPicture());
		result.setDescription(actorForm.getDescription());
		result.setBirthDate(actorForm.getBirthDate());
		result.setGenre(actorForm.getGenre());
		result.setDesiredRelationship(actorForm.getDesiredRelationship());

		coordinates.setCity(actorForm.getCity());
		coordinates.setCountry(actorForm.getCountry());
		coordinates.setProvince(actorForm.getProvince());
		coordinates.setState(actorForm.getState());
		finalCoordinates = this.coordinatesService.save(coordinates);

		result.setCoordinates(finalCoordinates);

		result.setUserAccount(userAccount);

		this.validator.validate(result, binding);
		userAccount.setPassword(encoder.encodePassword(actorForm.getUserAccount().getPassword(), null));
		return result;
	}

	public Chorbi reconstruct(final ActorForm actorForm, final Chorbi chorbi, final BindingResult binding) {
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		Chorbi result;
		Coordinates coordinates, finalCoordinates;

		result = new Chorbi();
		coordinates = new Coordinates();

		this.actorService.reconstruct(result, chorbi, actorForm);

		result.setPicture(actorForm.getPicture());
		result.setDescription(actorForm.getDescription());
		result.setBirthDate(actorForm.getBirthDate());
		result.setGenre(actorForm.getGenre());
		result.setDesiredRelationship(actorForm.getDesiredRelationship());

		coordinates.setCity(actorForm.getCity());
		coordinates.setCountry(actorForm.getCountry());
		coordinates.setProvince(actorForm.getProvince());
		coordinates.setState(actorForm.getState());
		finalCoordinates = this.coordinatesService.save(coordinates);

		result.setCoordinates(finalCoordinates);

		this.validator.validate(result, binding);
		result.getUserAccount().setPassword(encoder.encodePassword(actorForm.getUserAccount().getPassword(), null));
		return result;
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
