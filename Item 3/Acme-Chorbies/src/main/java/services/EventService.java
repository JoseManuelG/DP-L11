
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import repositories.EventRepository;
import antlr.debug.Event;

@Service
@Transactional
public class EventService {

	//Managed Repository--------------------------------------------------------------------
	@Autowired
	private EventRepository	eventRepository;

	//Supported Services--------------------------------------------------------------------
	@Autowired
	private ChorbiService	chorbiService;

	//	@Autowired
	//	private ManagerService	managerService;
	//
	//	@Autowired
	//	private RegisterService	registerService;

	@Autowired
	private Validator		validator;


	//Simple CRUD methods------------------------------------------------------------------

	public Event findOne(final int eventId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Event> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	//Other Bussnisnes methods------------------------------------------------------------

	public Collection<Event> findNextMonthEventsWithPlaces() {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean checkExpired(final Event event) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean checkSiteFree(final Event event) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean checkPrincipalRegistered(final Event event) {
		// TODO Auto-generated method stub
		return null;
	}

}
