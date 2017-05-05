/*
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package usecases;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import services.BannerService;
import services.ChorbiService;
import services.CoordinatesService;
import services.CreditCardService;
import services.LikesService;
import utilities.AbstractTest;
import domain.Chorbi;
import domain.Coordinates;
import domain.CreditCard;
import domain.Likes;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ChorbiTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ChorbiService		chorbiService;

	@Autowired
	private BannerService		bannerService;

	@Autowired
	private LikesService		likesService;

	@Autowired
	private CoordinatesService	coordinatesService;

	@Autowired
	private CreditCardService	creditCardService;


	// Tests ------------------------------------------------------------------

	//Caso de uso de banear un chorbi:
	//test positivo
	@Test
	public void banChorbiTest1() {
		this.templateBanChorbi("admin", "chorbi1", null);
	}
	//sin loguearse
	@Test
	public void banChorbiTest2() {
		this.templateBanChorbi(null, "chorbi1", IllegalArgumentException.class);
	}
	//no logeado como admin
	@Test
	public void banChorbiTest3() {
		this.templateBanChorbi("chorbi1", "chorbi1", IllegalArgumentException.class);
	}
	//chorbi no existente
	@Test
	public void banChorbiTest4() {
		this.templateBanChorbi("admin", "noExist", NullPointerException.class);
	}

	//Caso de uso de desbanear un chorbi:
	//test positivo
	@Test
	public void unbanChorbiTest1() {
		this.templateUnbanChorbi("admin", "chorbi6", null);
	}
	//sin loguearse
	@Test
	public void unbanChorbiTest2() {
		this.templateUnbanChorbi(null, "chorbi6", IllegalArgumentException.class);
	}
	//no logeado como admin
	@Test
	public void unbanChorbiTest3() {
		this.templateUnbanChorbi("chorbi1", "chorbi6", IllegalArgumentException.class);
	}
	//chorbi no existente
	@Test
	public void unbanChorbiTest4() {
		this.templateUnbanChorbi("admin", "noExist", NullPointerException.class);
	}

	// See a welcome page with a banner that advertises Acme projects, including Acme Pad-Thai
	//	, Acme BnB,	and Acme Car'n go! The banners must be selected randomly.

	@Test
	public void BannerPositiveTest() {
		final String banner = this.bannerService.randomBanner().getImage();
		Assert.notNull(banner);
	}

	//  Register as a chorbi

	@Test
	public void RegisterPositiveTest() {

		// Registro completo sin errores

		this.template("username", "password", "email@acme.com", "name", "surname", "+34123456789", "description", "love", "25/03/1994", "man", "http://www.test.com", null);
	}

	@Test
	public void RegisterNegativeTest2() {

		// Registro sin username
		this.template("", "password", "email@acme.com", "name", "surname", "+34123456789", "description", "love", "25/03/1994", "man", "http://www.test.com", ConstraintViolationException.class);

	}

	@Test
	public void RegisterNegativeTest3() {

		// Registro sin password
		this.template("username", "", "email@acme.com", "name", "surname", "+34123456789", "description", "love", "25/03/1994", "man", "http://www.test.com", ConstraintViolationException.class);
	}

	@Test
	public void RegisterNegativeTest4() {

		// Registro sin email

		this.template("username", "password", "", "name", "surname", "+34123456789", "description", "love", "25/03/1994", "man", "http://www.test.com", ConstraintViolationException.class);
	}

	@Test
	public void RegisterNegativeTest5() {

		// Registro sin nombre

		this.template("username", "password", "email@acme.com", "", "surname", "+34123456789", "description", "love", "25/03/1994", "man", "http://www.test.com", ConstraintViolationException.class);
	}

	@Test
	public void RegisterNegativeTest6() {

		// Registro sin apellidos

		this.template("username", "password", "email@acme.com", "name", "", "+34123456789", "description", "love", "25/03/1994", "man", "http://www.test.com", ConstraintViolationException.class);
	}

	@Test
	public void RegisterNegativeTest7() {

		// Registro sin telefono

		this.template("username", "password", "email@acme.com", "name", "surname", "", "description", "love", "25/03/1994", "man", "http://www.test.com", ConstraintViolationException.class);
	}

	@Test
	public void RegisterNegativeTest8() {

		// Registro sin descripción

		this.template("username", "password", "email@acme.com", "name", "surname", "+34123456789", "", "love", "25/03/1994", "man", "http://www.test.com", ConstraintViolationException.class);
	}

	@Test
	public void RegisterNegativeTest9() {

		// Registro sin relacion deseada

		this.template("username", "password", "email@acme.com", "name", "surname", "+34123456789", "description", "", "25/03/1994", "man", "http://www.test.com", ConstraintViolationException.class);
	}

	@Test
	public void RegisterNegativeTest10() {

		// Registro sin genero

		this.template("username", "password", "email@acme.com", "name", "surname", "+34123456789", "description", "love", "25/03/1994", "", "http://www.test.com", ConstraintViolationException.class);
	}

	@Test
	public void RegisterNegativeTest11() {

		// Registro sin genero

		this.template("username", "password", "email@acme.com", "name", "surname", "+34123456789", "description", "love", "25/03/1994", "man", "", ConstraintViolationException.class);
	}

	@Test
	public void LikePositiveTest1() {

		// Crear un like

		this.template("chorbi3", "chorbi1", "noExist", "test", 3, true, null);
	}

	@Test
	public void LikePositiveTest2() {

		// Borrar un like

		this.template("chorbi1", "noExist", "likes1Chorbi1", "test", 0, false, null);
	}
	@Test
	public void LikeNegativeTest1() {

		// Crear un like con comentario null

		this.template("chorbi3", "chorbi1", "noExist", null, 0, true, ConstraintViolationException.class);
	}

	//Caso de uso listar gente que le gustas si tienes credit card
	//test positivo
	@Test
	public void listPeopleWhoLikeMeTest1() {
		this.templatelistPeopleWhoLikeMe("chorbi1", null);
	}
	//sin loguearse
	@Test
	public void listPeopleWhoLikeMeTest2() {
		this.templatelistPeopleWhoLikeMe(null, IllegalArgumentException.class);
	}
	//logueado como manager
	@Test
	public void listPeopleWhoLikeMeTest3() {
		this.templatelistPeopleWhoLikeMe("manager1", IllegalArgumentException.class);
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateBanChorbi(final String username, final String chorbiBeanName, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);

			this.chorbiService.banChorbi(this.extract(chorbiBeanName));

			this.chorbiService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateUnbanChorbi(final String username, final String chorbiBeanName, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);

			this.chorbiService.unbanChorbi(this.extract(chorbiBeanName));

			this.chorbiService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void template(final String userName, final String password, final String email, final String name, final String surname, final String phone, final String description, final String desiredRelationship, final String birthDateString,
		final String genre, final String picture, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			final Chorbi chorbi = this.chorbiService.create();
			chorbi.setBirthDate(this.chorbiService.findOne(2471).getBirthDate());
			final UserAccount userAccount = new UserAccount();
			final Collection<Authority> authorities = new ArrayList<Authority>();
			final Authority authority = new Authority();
			Coordinates coordinates = this.coordinatesService.create();
			coordinates.setCity("test");
			coordinates.setCountry("test");
			coordinates.setProvince("test");
			coordinates.setState("test");
			coordinates = this.coordinatesService.save(coordinates);
			authority.setAuthority("CHORBI");
			authorities.add(authority);
			userAccount.setAuthorities(authorities);
			chorbi.setUserAccount(userAccount);
			chorbi.getUserAccount().setUsername(userName);
			chorbi.getUserAccount().setPassword(password);
			chorbi.setEmail(email);
			chorbi.setName(name);
			chorbi.setSurname(surname);
			chorbi.setPhone(phone);
			chorbi.setDescription(description);
			chorbi.setGenre(genre);
			chorbi.setDesiredRelationship(desiredRelationship);
			chorbi.setPicture(picture);
			chorbi.getUserAccount().setEnabled(true);
			chorbi.setCoordinates(coordinates);
			chorbi.setChargedFee(0.0);
			this.chorbiService.save(chorbi);
			this.chorbiService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void template(final String liker, final String likedBeanName, final String likesBeanName, final String comment, final Integer stars, final boolean whatToDo, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(liker);
			if (whatToDo) {
				final Likes likes = this.likesService.create(this.chorbiService.findOne(this.extract(likedBeanName)));
				likes.setComment(comment);
				likes.setStars(stars);
				this.likesService.save(likes);
			} else
				this.likesService.delete(this.likesService.findOne(this.extract(likesBeanName)));

			this.chorbiService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templatelistPeopleWhoLikeMe(final String username, final Class<?> expected) {
		Class<?> caught;
		Chorbi chorbi;
		CreditCard creditCard;
		caught = null;
		try {
			this.authenticate(username);

			chorbi = this.chorbiService.findChorbiByPrincipal();
			Assert.notNull(chorbi);
			creditCard = this.creditCardService.getCreditCardByPrincipal();
			if (creditCard != null)
				this.likesService.findReceivedLikesOfChorbi(chorbi.getId());
			else {
				//aqui se le reenviaria a crearse la credit card
			}
			this.chorbiService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
