
package services;

import java.util.Collection;
import java.util.Date;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.EventRepository;
import domain.Chorbi;
import domain.Event;
import domain.Register;

@Service
@Transactional
public class EventService {

	//Managed Repository--------------------------------------------------------------------
	@Autowired
	private EventRepository	eventRepository;

	//Supported Services--------------------------------------------------------------------
	@Autowired
	private ChorbiService	chorbiService;

	@Autowired
	private RegisterService	registerService;


	//Simple CRUD methods------------------------------------------------------------------

	public Event findOne(final int eventId) {
		return this.eventRepository.findOne(eventId);
	}

	public Collection<Event> findAll() {
		return this.eventRepository.findAll();
	}

	//Other Bussnisnes methods------------------------------------------------------------

	public Collection<Event> findNextMonthEventsWithPlaces() {
		LocalDate nextMonth;
		Date date, now;

		nextMonth = new LocalDate().plusMonths(1);
		date = nextMonth.toDateTimeAtStartOfDay().toDate();
		now = new Date();

		return this.eventRepository.findEventsWithPlacesBeforeDate(date, now);
	}

	public Boolean checkExpired(final Event event) {
		Date now;

		now = new Date();

		return now.after(event.getOrganisedMoment());
	}

	public Boolean checkSiteFree(final Event event) {
		Integer numberOfChorbies;

		numberOfChorbies = this.registerService.getNumberOfChorbiesForEvent(event.getId());
		if (numberOfChorbies == null)
			numberOfChorbies = 0;

		return event.getSeatsOffered() > numberOfChorbies;
	}

	public Boolean checkPrincipalIsRegistered(final Event event) {
		Chorbi chorbi;
		Register register;

		chorbi = this.chorbiService.findChorbiByPrincipal();
		register = this.registerService.findByEventAndChorbi(event.getId(), chorbi.getId());

		return register != null;
	}

}
