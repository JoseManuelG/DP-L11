
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public Register create(final Event event) {
		Chorbi chorbi;
		chorbi = this.chorbiService.findChorbiByPrincipal();
		Assert.notNull(event, "El evento no puede ser nulo");
		Assert.notNull(chorbi, "El chorbi no puede ser nulo");
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

		Assert.isTrue(!this.eventService.checkExpired(event), "register.error.expired");
		Assert.isTrue(!this.eventService.checkPrincipalIsRegistered(event), "register.error.double.register");
		Assert.isTrue(this.eventService.checkSiteFree(event), "register.error.free.site");

		result = this.registerRepository.save(register);
		return result;
	}
	public void delete(final Event event) {
		final Chorbi chorbi;
		Register register;
		Assert.notNull(event, "El evento no puede ser nulo");
		Assert.isTrue(event.getId() > 0, "La ID del event no puede ser 0");

		chorbi = this.chorbiService.findChorbiByPrincipal();
		Assert.notNull(chorbi, "El chorbi no puede ser nulo");

		register = this.findByEventAndChorbi(event.getId(), chorbi.getId());
		Assert.notNull(register, "Debes estar registrado en el evento");
		this.registerRepository.delete(register);
	}
	//Other Bussnisnes methods------------------------------------------------------------

	public Integer getNumberOfChorbiesForEvent(final int eventId) {
		return this.registerRepository.getNumberOfChorbiesForEvent(eventId);
	}

	public Register findByEventAndChorbi(final int eventId, final int chorbiId) {
		return this.registerRepository.findByEventAndChorbi(eventId, chorbiId);
	}
	public Collection<Register> findAllFromChorbi(final Chorbi chorbi) {
		return this.registerRepository.findAllFromChorbi(chorbi.getId());
	}

	public void deleteFromChorbi(final Chorbi chorbi) {
		final Collection<Register> registers;
		registers = this.findAllFromChorbi(chorbi);
		for (final Register r : registers)
			this.delete(r.getEvent());

	}

	public Collection<Chorbi> findChorbiesForEvent(final int eventId) {
		return this.registerRepository.findChorbiesForEvent(eventId);
	}

	public Page<Chorbi> findChorbiesForEvent(final int eventId, final Pageable pageable) {
		return this.registerRepository.findChorbiesForEventPaginated(eventId, pageable);
	}

	public void deleteRegistersForEvent(final Event event) {
		this.registerRepository.deleteRegistersForEvent(event.getId());

	}

}
