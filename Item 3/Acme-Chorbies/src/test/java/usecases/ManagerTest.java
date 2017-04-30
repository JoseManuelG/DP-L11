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
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.Authority;
import security.UserAccount;
import services.EventService;
import services.ManagerService;
import utilities.AbstractTest;
import domain.Event;
import domain.Manager;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ManagerTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private EventService	eventService;


	// Tests ------------------------------------------------------------------

	//  Register as a manager

	@Test
	public void RegisterPositiveTest() {

		// Registro completo sin errores

		this.registerTemplate("username", "password", "name", "surname", "email@acme.com", "+34123456789", "company", "vat", null);
	}

	@Test
	public void RegisterNegativeTest2() {

		// Registro sin username
		this.registerTemplate("", "password", "name", "surname", "email@acme.com", "+34123456789", "company", "vat", ConstraintViolationException.class);
	}

	@Test
	public void RegisterNegativeTest3() {

		// Registro sin password
		this.registerTemplate("username", "", "name", "surname", "email@acme.com", "+34123456789", "company", "vat", ConstraintViolationException.class);
	}

	@Test
	public void RegisterNegativeTest4() {

		// Registro sin email

		this.registerTemplate("username", "password", "name", "surname", "", "+34123456789", "company", "vat", ConstraintViolationException.class);
	}

	@Test
	public void RegisterNegativeTest5() {

		// Registro sin nombre

		this.registerTemplate("username", "password", null, "surname", "email@acme.com", "+34123456789", "company", "vat", ConstraintViolationException.class);
	}

	@Test
	public void RegisterNegativeTest6() {

		// Registro sin apellidos

		this.registerTemplate("username", "password", "name", null, "email@acme.com", "+34123456789", "company", "vat", ConstraintViolationException.class);
	}

	@Test
	public void RegisterNegativeTest7() {

		// Registro sin telefono

		this.registerTemplate("username", "password", "name", "surname", "email@acme.com", "", "company", "vat", ConstraintViolationException.class);
	}

	@Test
	public void RegisterNegativeTest8() {

		// Registro sin compañía

		this.registerTemplate("username", "password", "name", "surname", "email@acme.com", "+34123456789", null, "vat", ConstraintViolationException.class);
	}

	@Test
	public void RegisterNegativeTest9() {

		// Registro sin VAT

		this.registerTemplate("username", "password", "name", "surname", "email@acme.com", "+34123456789", "company", null, ConstraintViolationException.class);
	}

	// Create events

	@Test
	public void createEventPositiveTest() {

		// Test positivo

		this.createEventTemplate("title", "27/03/2018 19:46", "description", "http://picture.com/pic1.jpg", 10, "manager1", null);
	}

	@Test
	public void createEventNegativeTest1() {

		// Creación sin título

		this.createEventTemplate("", "27/03/2018 19:46", "description", "http://picture.com/pic1.jpg", 10, "manager1", ConstraintViolationException.class);
	}

	@Test
	public void createEventNegativeTest2() {

		// Creación en el pasado

		this.createEventTemplate("title", "27/03/2015 19:46", "description", "http://picture.com/pic1.jpg", 10, "manager1", IllegalArgumentException.class);
	}

	@Test
	public void createEventNegativeTest3() {

		// Imagen no url

		this.createEventTemplate("title", "27/03/2018 19:46", "description", "noturl", 10, "manager1", ConstraintViolationException.class);
	}

	@Test
	public void createEventNegativeTest4() {

		// Descripción vacía

		this.createEventTemplate("title", "27/03/2018 19:46", "", "http://picture.com/pic1.jpg", 10, "manager1", ConstraintViolationException.class);
	}

	@Test
	public void createEventNegativeTest5() {

		// Número de plazas inválido

		this.createEventTemplate("title", "27/03/2018 19:46", "description", "http://picture.com/pic1.jpg", 0, "manager1", IllegalArgumentException.class);
	}

	// Ancillary methods ------------------------------------------------------

	protected void registerTemplate(final String userName, final String password, final String name, final String surname, final String email, final String phone, final String company, final String vat, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			final Manager manager = this.managerService.create();
			final UserAccount userAccount = new UserAccount();
			final Collection<Authority> authorities = new ArrayList<Authority>();
			final Authority authority = new Authority();
			authority.setAuthority("MANAGER");
			authorities.add(authority);
			userAccount.setAuthorities(authorities);
			manager.setUserAccount(userAccount);
			manager.getUserAccount().setUsername(userName);
			manager.getUserAccount().setPassword(password);
			manager.setName(name);
			manager.setSurname(surname);
			manager.setEmail(email);
			manager.setPhone(phone);
			manager.setCompany(company);
			manager.setVAT(vat);
			manager.getUserAccount().setEnabled(true);
			manager.setChargedFee(0.0);
			this.managerService.save(manager);
			this.managerService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@SuppressWarnings("deprecation")
	private void createEventTemplate(final String title, final String organisedMomentString, final String description, final String picture, final int seatsOffered, final String manageBeanName, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			final Manager manager = this.managerService.findOne(this.extract(manageBeanName));
			final String username = manager.getUserAccount().getUsername();
			this.authenticate(username);
			final Event event = this.eventService.create();
			event.setTitle(title);
			final Date organisedMoment = new Date(organisedMomentString);
			event.setOrganisedMoment(organisedMoment);
			event.setDescription(description);
			event.setPicture(picture);
			event.setSeatsOffered(seatsOffered);

			event.setManager(manager);
			this.eventService.save(event);
			this.eventService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);

	}
}
