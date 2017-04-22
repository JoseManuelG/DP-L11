
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RegisterRepository;
import domain.Chorbi;
import domain.Event;
import domain.Register;

@Service
@Transactional
public class RegisterService {

	//Managed Repository--------------------------------------------------------------------
	@Autowired
	private RegisterRepository	registerRepository;

	//Supported Services--------------------------------------------------------------------
	@Autowired
	private EventService		eventService;
	@Autowired
	private ChorbiService		chorbiService;


	//Simple CRUD methods------------------------------------------------------------------
	public Register create(final Chorbi chorbi, final Event event) {
		Assert.notNull(chorbi, "El chorbi no puede ser nulo");
		Assert.notNull(event, "El evento no puede ser nulo");

		Assert.isTrue(chorbi.getId() > 0, "La ID del chorbi no puede ser 0");
		Assert.isTrue(event.getId() > 0, "La ID del event no puede ser 0");
		final Register result = new Register();
		result.setChorbi(chorbi);
		result.setEvent(event);
		return result;
	}

	public Register save(final Register register) {
		Event event;
		Register result;
		event = register.getEvent();
		//En estos Asserts comprobamos si el principal se ha registrado ya, si queda sitio libre y si el evento ya ha pasado.
		Assert.isTrue(this.eventService.checkPrincipalIsRegistered(event), "No puedes registrarte dos veces en el mismo evento");
		Assert.isTrue(!this.eventService.checkSiteFree(event), "No quedan plazas en el evento");
		Assert.isTrue(this.eventService.checkExpired(event), "No puedes registrarte en un evento pasado");

		result = this.registerRepository.save(register);
		return result;
	}
	public void delete(final Event event) {
		final Chorbi chorbi;
		Register register;

		chorbi = this.chorbiService.findChorbiByPrincipal();
		register = this.findByEventAndChorbi(event.getId(), chorbi.getId());

		this.registerRepository.delete(register);
	}
	//Other Bussnisnes methods------------------------------------------------------------

	public Integer getNumberOfChorbiesForEvent(final int eventId) {
		return this.registerRepository.getNumberOfChorbiesForEvent(eventId);
	}

	public Register findByEventAndChorbi(final int eventId, final int chorbiId) {
		return this.registerRepository.findByEventAndChorbi(eventId, chorbiId);
	}

}
