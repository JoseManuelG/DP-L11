
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

	@Query("select r.event from Register r group by r.event order by r.event.seatsOffered - count(r)")
	public Collection<Event> findAllSorted();

	@Query("select e from Event e where ?2 <= e.organisedMoment and e.organisedMoment <= ?1 and e.seatsOffered > (select count(r) from Register r where r.event = e)")
	public Collection<Event> findEventsWithPlacesBeforeDate(Date date, Date now);

	@Query("select r.event from Register r where ?2 <= r.event.organisedMoment and r.event.organisedMoment <= ?1 and r.event.seatsOffered > (select count(r2) from Register r2 where r2.event = r.event) group by r.event order by r.event.seatsOffered - count(r)")
	public Collection<Event> findEventsWithPlacesBeforeDateSorted(Date date, Date now);

	@Query("select r.event from Register r where r.chorbi.id = ?1")
	public Collection<Event> findAllFromChorbi(int chorbiId);

	@Query("select r.event from Register r where r.event in (select r2.event from Register r2 where r2.chorbi.id=?1) group by r.event order by r.event.seatsOffered - count(r)")
	public Collection<Event> findAllFromChorbiSorted(int id);

	@Query("select e from Event e where e.manager.id = ?1")
	public Collection<Event> findAllFromManager(int managerId);

	@Query("select r.event from Register r where r.event.manager.id = ?1 group by r.event order by r.event.seatsOffered - count(r)")
	public Collection<Event> findAllFromManagerSorted(int id);

}
