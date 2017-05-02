
package services;

import java.util.Collection;
import java.util.Date;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
		Assert.isTrue(event.getId() == 0 || this.managerService.findManagerByPrincipal().equals(event.getManager()), "event.error.notowner");
		Assert.isTrue(event.getOrganisedMoment().after(new Date()), "event.error.invalid.date");
		if (event.getId() == 0) {
			Assert.isTrue(event.getSeatsOffered() > 0, "event.error.noseats");
			this.managerOperationsForNewEvent();
		} else
			Assert.isTrue(event.getSeatsOffered() >= this.registerService.getNumberOfChorbiesForEvent(event.getId()), "event.error.chorbies.registered");

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

	public Collection<Object[]> findAllAndFreePlaces() {
		return this.eventRepository.findAllAndFreePlaces();
	}

	public Collection<Object[]> findAllSortedAndFreePlaces() {
		return this.eventRepository.findAllSortedAndFreePlaces();
	}

	public Collection<Object[]> findNextMonthEventsWithPlacesAndFreePlaces() {
		LocalDate nextMonth;
		Date date, now;

		nextMonth = new LocalDate().plusMonths(1);
		date = nextMonth.toDateTimeAtStartOfDay().toDate();
		now = new Date();

		return this.eventRepository.findEventsWithPlacesBeforeDateAndFreePlaces(date, now);
	}

	public Collection<Object[]> findNextMonthEventsWithPlacesSortedAndFreePlaces() {
		LocalDate nextMonth;
		Date date, now;

		nextMonth = new LocalDate().plusMonths(1);
		date = nextMonth.toDateTimeAtStartOfDay().toDate();
		now = new Date();

		return this.eventRepository.findEventsWithPlacesBeforeDateSortedAndFreePlaces(date, now);
	}

	public Collection<Event> findNextMonthEventsWithPlaces() {
		LocalDate nextMonth;
		Date date, now;

		nextMonth = new LocalDate().plusMonths(1);
		date = nextMonth.toDateTimeAtStartOfDay().toDate();
		now = new Date();

		return this.eventRepository.findEventsWithPlacesBeforeDate(date, now);
	}

	public Collection<Object[]> findAllFromPrincipalChorbiAndFreePlaces() {
		Chorbi chorbi;
		int chorbiId;

		chorbi = this.chorbiService.findChorbiByPrincipal();

		Assert.notNull(chorbi);
		chorbiId = chorbi.getId();

		return this.eventRepository.findAllFromChorbiAndFreePlaces(chorbiId);
	}

	public Collection<Object[]> findAllFromPrincipalChorbiSortedAndFreePlaces() {
		Chorbi chorbi;
		int chorbiId;

		chorbi = this.chorbiService.findChorbiByPrincipal();

		Assert.notNull(chorbi);
		chorbiId = chorbi.getId();

		return this.eventRepository.findAllFromChorbiSortedAndFreePlaces(chorbiId);
	}

	public Collection<Object[]> findAllFromPrincipalManagerAndFreePlaces() {
		Manager manager;

		manager = this.managerService.findManagerByPrincipal();

		return this.eventRepository.findAllFromManagerAndFreePlaces(manager.getId());
	}

	public Collection<Event> findAllFromPrincipalManager() {
		Manager manager;

		manager = this.managerService.findManagerByPrincipal();

		return this.eventRepository.findAllFromManager(manager.getId());
	}

	public Collection<Object[]> findAllFromPrincipalManagerSortedAndFreePlaces() {
		Manager manager;

		manager = this.managerService.findManagerByPrincipal();

		return this.eventRepository.findAllFromManagerSortedAndFreePlaces(manager.getId());
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

	public void notifyChangesToAssistantChorbies(final Event event, final Boolean edited) {
		Page<Chorbi> page;
		PageRequest pageRequest;
		int numberOfPages;
		Customer principal;

		principal = this.customerService.findCustomerByPrincipal();
		Assert.isTrue(event.getManager().equals(principal), "chirp.broadcast.principal.error");

		pageRequest = new PageRequest(0, 100);
		page = this.registerService.findChorbiesForEvent(event.getId(), pageRequest);

		numberOfPages = page.getTotalPages();

		for (int i = 0; i < numberOfPages; i++) {
			pageRequest = new PageRequest(i, 100);
			page = this.registerService.findChorbiesForEvent(event.getId(), pageRequest);
			for (final Chorbi c : page.getContent()) {
				Chirp chirp;

				chirp = this.chirpService.create(c.getId());
				chirp.setSender(null);
				if (edited)
					chirp.setSubject("Un evento al que asistes ha cambiado");
				else
					chirp.setSubject("Un evento al que asistes ha sido cancelado");
				chirp.setText("El evento es: " + event.getTitle());
				this.chirpService.save(chirp);

			}
		}
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

	public void saveAndNotify(final Event event) {
		Event result;

		result = this.save(event);
		this.notifyChangesToAssistantChorbies(result, true);
	}

	public void deleteAndNotify(final Event event) {
		this.notifyChangesToAssistantChorbies(event, false);
		this.registerService.deleteRegistersForEvent(event);
		this.delete(event);
	}
}
