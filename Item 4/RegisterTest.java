/*
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.EventService;
import services.RegisterService;
import utilities.AbstractTest;
import domain.Event;
import domain.Register;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RegisterTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private RegisterService	registerService;
	@Autowired
	private EventService	eventService;


	// Tests ------------------------------------------------------------------

	//Caso de uso de registrarse a un evento:
	//Test positivo 
	@Test
	public void CreateRegisterTest1() {
		this.templateCreateRegister("chorbi2", false, "event2", null);
	}

	//Test positivo con lista ordenada
	@Test
	public void CreateRegisterTest2() {
		this.templateCreateRegister("chorbi2", true, "event2", null);
	}

	//Test negativo no logueado
	@Test
	public void createRegisterTest3() {
		this.templateCreateRegister(null, false, "event2", IllegalArgumentException.class);
	}
	//Test negativo logueado como manager
	@Test
	public void createRegisterTest4() {
		this.templateCreateRegister("manager2", true, "event2", IllegalArgumentException.class);
	}
	//Test negativo logueado como admin
	@Test
	public void createRegisterTest5() {
		this.templateCreateRegister("admin", false, "event2", IllegalArgumentException.class);
	}
	//Test negativo ya estas registrado
	@Test
	public void createRegisterTest6() {
		this.templateCreateRegister("chorbi2", true, "event1", IllegalArgumentException.class);
	}
	//Test negativo la id es 0
	@Test
	public void createRegisterTest7() {
		this.templateCreateRegister("chorbi2", false, "noExist", IllegalArgumentException.class);
	}
	//Test negativo evento pasado
	@Test
	public void createRegisterTest8() {
		this.templateCreateRegister("chorbi2", true, "event3", IllegalArgumentException.class);
	}
	//Caso de Uso des-registrarse a un evento
	//Test positivo 
	@Test
	public void unregisterTest1() {
		this.templateUnregister("chorbi2", false, "event1", null);
	}
	//Test positivo con lista ordenada
	@Test
	public void unregisterTest2() {
		this.templateUnregister("chorbi2", true, "event1", null);
	}
	//Test negativo no logueado
	@Test
	public void unregisterTest3() {
		this.templateUnregister(null, false, "event1", IllegalArgumentException.class);
	}
	//Test negativo logueado como manager
	@Test
	public void unregisterTest4() {
		this.templateUnregister("manager2", true, "event1", IllegalArgumentException.class);
	}
	//Test negativo logueado como admin
	@Test
	public void unregisterTest5() {
		this.templateUnregister("admin", false, "event1", IllegalArgumentException.class);
	}
	//Test negativo no estas registrado
	@Test
	public void unregisterTest6() {
		this.templateUnregister("chorbi2", true, "event2", IllegalArgumentException.class);
	}
	//Test negativo la id es 0
	@Test
	public void unregisterTest7() {
		this.templateUnregister("chorbi2", false, "noExist", IllegalArgumentException.class);
	}
	//Test negativo evento pasado
	@Test
	public void unregisterTest8() {
		this.templateUnregister("chorbi2", true, "event3", IllegalArgumentException.class);
	}

	// Ancillary methods ------------------------------------------------------
	protected void templateCreateRegister(final String username, final Boolean sorted, final String eventBeanName, final Class<?> expected) {
		Class<?> caught;
		final Register register;
		final Event event;
		caught = null;
		try {
			this.authenticate(username);

			if (!sorted)
				this.eventService.findNextMonthEventsWithPlacesAndFreePlaces();
			else
				this.eventService.findNextMonthEventsWithPlacesSortedAndFreePlaces();

			event = this.eventService.findOne(this.extract(eventBeanName));
			register = this.registerService.create(event);
			this.registerService.save(register);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	protected void templateUnregister(final String username, final Boolean sorted, final String eventBeanName, final Class<?> expected) {
		Class<?> caught;
		final Event event;
		caught = null;
		try {
			this.authenticate(username);

			if (!sorted)
				this.eventService.findAllFromPrincipalChorbiAndFreePlaces();
			else
				this.eventService.findAllFromPrincipalChorbiSortedAndFreePlaces();

			event = this.eventService.findOne(this.extract(eventBeanName));

			this.registerService.delete(event);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
