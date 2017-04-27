
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

	@Query("select r.event, r.event.seatsOffered - count(r) from Register r group by r.event")
	public Collection<Object[]> findAllAndFreePlaces();

	@Query("select r.event, r.event.seatsOffered - count(r) from Register r group by r.event order by r.event.organisedMoment")
	public Collection<Object[]> findAllSortedAndFreePlaces();

	@Query("select r.event, r.event.seatsOffered - count(r) from Register r where ?2 <= r.event.organisedMoment and r.event.organisedMoment <= ?1 and r.event.seatsOffered > (select count(r2) from Register r2 where r2.event = r.event) group by r.event")
	public Collection<Object[]> findEventsWithPlacesBeforeDateAndFreePlaces(Date date, Date now);

	@Query("select r.event, r.event.seatsOffered - count(r) from Register r where ?2 <= r.event.organisedMoment and r.event.organisedMoment <= ?1 and r.event.seatsOffered > (select count(r2) from Register r2 where r2.event = r.event) group by r.event order by r.event.organisedMoment")
	public Collection<Object[]> findEventsWithPlacesBeforeDateSortedAndFreePlaces(Date date, Date now);

	@Query("select r.event from Register r where ?2 <= r.event.organisedMoment and r.event.organisedMoment <= ?1 and r.event.seatsOffered > (select count(r2) from Register r2 where r2.event = r.event) group by r.event")
	public Collection<Event> findEventsWithPlacesBeforeDate(Date date, Date now);

	@Query("select r.event, r.event.seatsOffered - count(r) from Register r where r.event in (select r2.event from Register r2 where r2.chorbi.id=?1) group by r.event")
	public Collection<Object[]> findAllFromChorbiAndFreePlaces(int chorbiId);

	@Query("select r.event, r.event.seatsOffered - count(r) from Register r where r.event in (select r2.event from Register r2 where r2.chorbi.id=?1) group by r.event order by r.event.organisedMoment")
	public Collection<Object[]> findAllFromChorbiSortedAndFreePlaces(int id);

	@Query("select r.event, r.event.seatsOffered - count(r) from Register r where r.event.manager.id = ?1 group by r.event")
	public Collection<Object[]> findAllFromManagerAndFreePlaces(int managerId);

	@Query("select r.event, r.event.seatsOffered - count(r) from Register r where r.event.manager.id = ?1 group by r.event order by r.event.organisedMoment")
	public Collection<Object[]> findAllFromManagerSortedAndFreePlaces(int id);

	@Query("select e from Event e where e.manager.id = ?1")
	public Collection<Event> findAllFromManager(int id);

}
