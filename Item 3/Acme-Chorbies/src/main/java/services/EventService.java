
package services;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.EventRepository;
import domain.Chorbi;
import domain.Event;
import domain.Manager;
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

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private EventComparator	eventComparator;


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

	public Collection<Event> findAllFromPrincipalChorbi() {
		Chorbi chorbi;

		chorbi = this.chorbiService.findChorbiByPrincipal();

		return this.eventRepository.findAllFromChorbi(chorbi.getId());
	}

	public Collection<Event> findAllFromPrincipalManager() {
		Manager manager;

		manager = this.managerService.findManagerByPrincipal();

		return this.eventRepository.findAllFromManager(manager.getId());
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

	public void sort(final List<Event> events) {
		//		Comparator<Event> comparator;
		//
		//		comparator = new EventComparator();

		Collections.sort(events, this.eventComparator);

	}

	//	public class EventComparator implements Comparator<Event> {
	//
	//		@Autowired
	//		private RegisterService	registerService;
	//
	//
	//		@Override
	//		public int compare(final Event event1, final Event event2) {
	//			Integer numberOfChorbies1, numberOfChorbies2, freeSeats1, freeSeats2;
	//
	//			numberOfChorbies1 = this.registerService.getNumberOfChorbiesForEvent(event1.getId());
	//			numberOfChorbies2 = this.registerService.getNumberOfChorbiesForEvent(event2.getId());
	//
	//			freeSeats1 = event1.getSeatsOffered() - numberOfChorbies1;
	//			freeSeats2 = event2.getSeatsOffered() - numberOfChorbies2;
	//
	//			return freeSeats1.compareTo(freeSeats2);
	//		}
	//
	//	}
}
