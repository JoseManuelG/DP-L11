
package services;

import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Event;

@Service
@Transactional
public class EventComparator implements Comparator<Event> {

	@Autowired
	private RegisterService	registerService;


	@Override
	public int compare(final Event event1, final Event event2) {
		Integer numberOfChorbies1, numberOfChorbies2, freeSeats1, freeSeats2;

		numberOfChorbies1 = this.registerService.getNumberOfChorbiesForEvent(event1.getId());
		numberOfChorbies2 = this.registerService.getNumberOfChorbiesForEvent(event2.getId());

		freeSeats1 = event1.getSeatsOffered() - numberOfChorbies1;
		freeSeats2 = event2.getSeatsOffered() - numberOfChorbies2;

		return freeSeats1.compareTo(freeSeats2);
	}

}
