
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

	@Query("select e from Event e where e.organisedMoment <= ?1 and e.seatsOffered > (select count(r) from Register r where r.event = e)")
	public Collection<Event> findEventsWithPlacesBeforeDate(Date date);
}
