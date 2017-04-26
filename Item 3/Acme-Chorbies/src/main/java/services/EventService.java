
package services;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.EventRepository;
import domain.Chirp;
import domain.Chorbi;
import domain.CreditCard;
import domain.Customer;
import domain.Event;
import domain.Manager;
import domain.Register;

@Service
@Transactional
public class EventService {

	//Managed Repository--------------------------------------------------------------------
	@Autowired
	private EventRepository			eventRepository;

	//Supported Services--------------------------------------------------------------------
	@Autowired
	private ChorbiService			chorbiService;

	@Autowired
	private RegisterService			registerService;

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private EventComparator			eventComparator;

	@Autowired
	private ChirpService			chirpService;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private Validator				validator;


	//Simple CRUD methods------------------------------------------------------------------

	public Event create() {
		final Event event = new Event();
		return event;
	}

	public Event findOne(final int eventId) {
		return this.eventRepository.findOne(eventId);
	}

	public Collection<Event> findAll() {
		return this.eventRepository.findAll();
	}

	public Event save(final Event event) {
		Event result;
		Assert.notNull(event, "event.error.null");
		Assert.isTrue(event.getId() == 0 ||

		this.managerService.findManagerByPrincipal().equals(event.getManager()), "event.error.notowner");
		Assert.isTrue(event.getOrganisedMoment().after(new Date()), "event.error.invalid.date");
		if (event.getId() == 0) {
			Assert.isTrue(event.getSeatsOffered() > 0, "event.error.noseats");
			this.managerOperationsForNewEvent();
		}

		result = this.eventRepository.save(event);
		return result;
	}

	public void delete(final Event event) {
		final Event dd = this.findOne(event.getId());
		Assert.notNull(dd, "event.null.error");
		Assert.isTrue(this.eventRepository.exists(dd.getId()), "event.exists.error");
		Assert.isTrue(this.managerService.findManagerByPrincipal().equals(dd.getManager()), "event.error.notowner");
		this.eventRepository.delete(event);

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

	public Event reconstruct(final Event event, final BindingResult binding) {
		final Event result = this.create();
		if (event.getId() != 0) {
			final Event savedEvent = this.findOne(event.getId());
			result.setId(savedEvent.getId());
			result.setVersion(savedEvent.getVersion());
			final Manager manager = this.managerService.findOne(savedEvent.getManager().getId());
			result.setManager(manager);

		} else
			result.setManager(this.managerService.findManagerByPrincipal());

		result.setTitle(event.getTitle());
		result.setOrganisedMoment(event.getOrganisedMoment());
		result.setDescription(event.getDescription());
		result.setPicture(event.getPicture());
		result.setSeatsOffered(event.getSeatsOffered());
		this.validator.validate(result, binding);

		return result;
	}

	public void sort(final List<Event> events) {
		//		Comparator<Event> comparator;
		//
		//		comparator = new EventComparator();

		Collections.sort(events, this.eventComparator);

	}

	public void notifyChangesToAssistantChorbies(final Event event) {
		final Collection<Chorbi> assistants = this.registerService.findChorbiesForEvent(event.getId());
		final Collection<Chirp> chirps = new LinkedList<Chirp>();
		for (final Chorbi c : assistants) {
			final Chirp chirp = this.chirpService.create(c.getId());
			chirp.setSender(null);
			chirp.setSubject("Un evento al que asistes ha cambiado");
			chirp.setText("El evento es: " + event.getTitle());
			chirps.add(chirp);

		}
		this.chirpService.save(chirps);
		//TODO: Diferenciar borrado y edición
	}

	private void managerOperationsForNewEvent() {
		final CreditCard creditCard = this.creditCardService.getCreditCardByPrincipal();
		Assert.notNull(creditCard, "creditCard.noCreditCard");
		Assert.isTrue(this.creditCardService.checkCreditCard(creditCard), "creditCard.expired.error");
		final Customer customer = this.customerService.findCustomerByPrincipal();
		customer.setChargedFee(customer.getChargedFee() + this.configurationService.findConfiguration().getManagerFee());
		this.actorService.save(customer);
	}

	public void flush() {
		this.eventRepository.flush();
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
