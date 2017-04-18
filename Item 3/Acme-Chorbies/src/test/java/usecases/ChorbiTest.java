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
import services.LikesService;
import utilities.AbstractTest;
import domain.Chorbi;
import domain.Coordinates;
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


	// Tests ------------------------------------------------------------------

	//Caso de uso de banear un chorbi:
	//test positivo
	@Test
	public void banChorbiTest1() {
		this.templateBanChorbi("admin", 1098, null);
	}
	//sin loguearse
	@Test
	public void banChorbiTest2() {
		this.templateBanChorbi(null, 1098, IllegalArgumentException.class);
	}
	//no logeado como admin
	@Test
	public void banChorbiTest3() {
		this.templateBanChorbi("chorbi1", 1098, IllegalArgumentException.class);
	}
	//chorbi no existente
	@Test
	public void banChorbiTest4() {
		this.templateBanChorbi("admin", 288, NullPointerException.class);
	}

	//Caso de uso de desbanear un chorbi:
	//test positivo
	@Test
	public void unbanChorbiTest1() {
		this.templateUnbanChorbi("admin", 1098, null);
	}
	//sin loguearse
	@Test
	public void unbanChorbiTest2() {
		this.templateUnbanChorbi(null, 1098, IllegalArgumentException.class);
	}
	//no logeado como admin
	@Test
	public void unbanChorbiTest3() {
		this.templateUnbanChorbi("chorbi1", 1098, IllegalArgumentException.class);
	}
	//chorbi no existente
	@Test
	public void unbanChorbiTest4() {
		this.templateUnbanChorbi("admin", 288, NullPointerException.class);
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

		this.template("chorbi3", 1101, 0, "test", true, null);
	}

	@Test
	public void LikePositiveTest2() {

		// Borrar un like

		this.template("chorbi1", 0, 1103, "test", false, null);
	}
	@Test
	public void LikeNegativeTest1() {

		// Crear un like con comentario null

		this.template("chorbi3", 1101, 0, null, true, ConstraintViolationException.class);
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateBanChorbi(final String username, final int chorbiId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);

			this.chorbiService.banChorbi(chorbiId);

			this.chorbiService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateUnbanChorbi(final String username, final int chorbiId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);

			this.chorbiService.unbanChorbi(chorbiId);

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
			chorbi.setBirthDate(this.chorbiService.findOne(1096).getBirthDate());
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
			this.chorbiService.save(chorbi);
			this.chorbiService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void template(final String liker, final Integer likedId, final Integer likesId, final String comment, final boolean whatToDo, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(liker);
			if (whatToDo) {
				final Likes likes = this.likesService.create(this.chorbiService.findOne(likedId));
				likes.setComment(comment);
				this.likesService.save(likes);
			} else
				this.likesService.delete(this.likesService.findOne(likesId));

			this.chorbiService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
