/*
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package usecases;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ChirpService;
import services.EventService;
import services.RegisterService;
import utilities.AbstractTest;
import domain.Chirp;
import domain.Chorbi;
import forms.ChirpBroadcastForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EventTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private EventService	eventService;

	@Autowired
	private ChirpService	chirpService;

	@Autowired
	private RegisterService	registerService;


	// Tests ------------------------------------------------------------------

	//Caso de uso listar los eventos
	//test positivo de eventos del chorbi
	@Test
	public void listEventsTest1() {
		this.templateListNearEvents("chorbi1", true, null);
	}
	//test positivo de eventos del manager
	@Test
	public void listEventsTest2() {
		this.templateListNearEvents("manager1", false, null);
	}
	//no logueado viendo los de chorbi
	@Test
	public void listEventsTest3() {
		this.templateListNearEvents(null, true, IllegalArgumentException.class);
	}
	//no logueado viendo los de manager
	@Test
	public void listEventsTest4() {
		this.templateListNearEvents(null, false, IllegalArgumentException.class);
	}
	//logueado como chorbi viendo los de manager
	@Test
	public void listEventsTest5() {
		this.templateListNearEvents("chorbi", false, IllegalArgumentException.class);
	}
	//logueado como manager viendo los de chorbi
	@Test
	public void listEventsTest6() {
		this.templateListNearEvents("manager1", true, IllegalArgumentException.class);
	}
	//Caso de uso mensaje broadcast de evento
	//test positivo
	@Test
	public void broadcastEventMessageTest1() {
		this.templatebroadcastEventMessage("manager1", "testsubject", "testText", "event1", null);
	}
	//sin loguearse
	@Test
	public void broadcastEventMessageTest2() {
		this.templatebroadcastEventMessage(null, "testsubject", "testText", "event1", IllegalArgumentException.class);
	}
	//logueado como chorbi
	@Test
	public void broadcastEventMessageTest3() {
		this.templatebroadcastEventMessage("chorbi1", "testsubject", "testText", "event1", IllegalArgumentException.class);
	}
	//intentando hacer un mensaje de otro manager
	@Test
	public void broadcastEventMessageTest4() {
		this.templatebroadcastEventMessage("manager2", "testsubject", "testText", "event1", IllegalArgumentException.class);
	}
	// Ancillary methods ------------------------------------------------------

	protected void templateListNearEvents(final String username, final boolean chorbi, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			this.eventService.findAllAndFreePlaces();

			if (chorbi) {
				this.eventService.findAllFromPrincipalChorbiAndFreePlaces();
				this.eventService.findAllFromPrincipalChorbiSortedAndFreePlaces();
			} else {
				this.eventService.findAllFromPrincipalManagerAndFreePlaces();
				this.eventService.findAllFromPrincipalManagerSortedAndFreePlaces();
			}

			this.eventService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templatebroadcastEventMessage(final String username, final String subject, final String text, final String eventBeanName, final Class<?> expected) {
		Class<?> caught;
		ChirpBroadcastForm form;
		List<Chirp> chirps;
		List<Chorbi> recipients;

		caught = null;
		try {
			this.authenticate(username);

			form = new ChirpBroadcastForm();
			chirps = new ArrayList<Chirp>();
			recipients = new ArrayList<Chorbi>();

			form.setEvent(this.extract(eventBeanName));
			form.setSubject(subject);
			form.setText(text);
			chirps = new LinkedList<Chirp>();
			recipients = new LinkedList<Chorbi>(this.registerService.findChorbiesForEvent(form.getEvent()));

			for (final Chorbi chorbi : recipients) {
				Chirp aux;

				aux = this.chirpService.create(chorbi.getId());
				aux.setText(form.getText());
				aux.setSubject(form.getSubject());

				chirps.add(aux);
			}

			this.chirpService.save(chirps, form.getAttachments(), form.getEvent());
			this.chirpService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
