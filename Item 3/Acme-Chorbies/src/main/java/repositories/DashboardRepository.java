
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chorbi;
import domain.DomainEntity;
import domain.Manager;

@Repository
public interface DashboardRepository extends JpaRepository<DomainEntity, Integer> {

	// Dashboard - 01
	@Query("select count(c), c.coordinates.country from Chorbi c group by c.coordinates.country")
	public List<Object[]> numberOfChorbiesPerCountry();

	// Dashboard - 01
	@Query("select count(c), c.coordinates.city from Chorbi c group by c.coordinates.city")
	public List<Object[]> numberOfChorbiesPerCity();

	// Dashboard - 02	
	@Query("select min(DATEDIFF(CURRENT_DATE, c.birthDate)/365.256363004) from Chorbi c")
	public Integer minAgeOfChorbies();

	// Dashboard - 02	
	@Query("select max(DATEDIFF(CURRENT_DATE, c.birthDate)/365.256363004) from Chorbi c")
	public Integer maxAgeOfChorbies();

	// Dashboard - 02	
	@Query("select avg(DATEDIFF(CURRENT_DATE, c.birthDate)/365.256363004) from Chorbi c")
	public Double averageAgeOfChorbies();

	// Dashboard - 03
	@Query("select count(c) from CreditCard c where c.expirationYear > YEAR(CURRENT_DATE) and (c.expirationMonth >= MONTH(CURRENT_DATE) and not (c.expirationMonth = MONTH(CURRENT_DATE) and ?1 = TRUE))")
	public Double countValidCreditCard(boolean lastDayOfMonth);

	// Dashboard - 04
	@Query("select count(c) from Chorbi c where c.desiredRelationship='activities'")
	public Double countChorbiesWhoDesireActivities();

	// Dashboard - 04
	@Query("select count(c) from Chorbi c where c.desiredRelationship='friendship'")
	public Double countChorbiesWhoDesireFriendship();

	// Dashboard - 04
	@Query("select count(c) from Chorbi c where c.desiredRelationship='love'")
	public Double countChorbiesWhoDesireLove();

	// Dashboard - 05
	@Query("select c from Likes l, Chorbi c where c=l.liked group by c order by count(c) desc")
	public List<Chorbi> findChorbiesOrderedByLikes();

	// Dashboard - 05
	@Query("select c from Chorbi c where c not in (select distinct l.liked from Likes l)")
	public List<Chorbi> findChorbiesWithoutLikes();

	//Dashboard - 06
	@Query("select count(l.liked) from Likes l group by l.liked order by count(l.liked) asc")
	public List<Long> minNumberOfLikesPerChorbi();

	//Dashboard - 06
	@Query("select count(l.liked) from Likes l group by l.liked order by count(l.liked) desc")
	public List<Long> maxNumberOfLikesPerChorbi();

	//Dashboard - 07
	@Query("select count(m) from Chirp m where m.isSender=false and m.recipient is not null")
	public Double averageOfChirpsReceivedPerChorbi();

	//Dashboard - 07
	@Query("select count(m) from Chirp m where m.isSender=false and m.recipient is not null group by m.recipient.id order by count(m) asc")
	public List<Long> minOfChirpsReceivedPerChorbi();

	//Dashboard - 07
	@Query("select count(m) from Chirp m where m.isSender=false and m.recipient is not null group by m.recipient.id order by count(m) desc")
	public List<Long> maxOfChirpsReceivedPerChorbi();

	//Dashboard - 08
	@Query("select count(m) from Chirp m where m.isSender=true and m.sender is not null")
	public Double averageOfChirpsSentPerChorbi();

	//Dashboard - 08
	@Query("select count(m) from Chirp m where m.isSender=true and m.sender is not null group by m.sender.id order by count(m) asc")
	public List<Long> minOfChirpsSentPerChorbi();

	//Dashboard - 08
	@Query("select count(m) from Chirp m where m.isSender=true and m.sender is not null group by m.sender.id order by count(m) desc")
	public List<Long> maxOfChirpsSentPerChorbi();

	//Dashboard - 09
	@Query("select m.recipient from Chirp m where m.isSender=false and m.recipient is not null group by m.recipient having count(m)=?1 order by count(m) desc")
	public List<Chorbi> findChorbiesWhoGotMoreChirps(Long max);

	//Dashboard - 10
	@Query("select m.sender from Chirp m where m.isSender=true and m.sender is not null group by m.sender having count(m)=?1 order by count(m) desc")
	public List<Chorbi> findChorbiesWhoSentMoreChirps(Long max);

	//Dashboard - 11
	@Query("select m, count(e) from Event e, Manager m where m=e.manager group by m order by count(e) desc")
	public List<Object[]> getManagersOrderedByEvents();

	//Dashboard - 11
	@Query("select m from Manager m where m not in (select distinct e.manager from Event e)")
	public List<Manager> getManagersWithNoEvents();

	//Dashboard - 12 (no necesita query aqui)

	//Dashboard - 13
	@Query("select c, count(r) from Register r, Chorbi c where c=r.chorbi group by c order by count(r) desc")
	public List<Object[]> getChorbiesOrderedByRegisters();

	//Dashboard - 13
	@Query("select c from Chorbi c where c not in (select distinct r.chorbi from Register r)")
	public List<Chorbi> getChorbiesWithNoRegisters();

	//Dashboard - 14 (no necesita query aqui)

	//Dashboard - 15
	@Query("select c, min(r.stars), max(r.stars), avg(r.stars) from Likes r, Chorbi c where c=r.liked group by c")
	public List<Object[]> getChorbiesWithMinMaxAvgStars();

	//Dashboard - 15 y 16
	@Query("select c from Chorbi c where c not in (select distinct r.liked from Likes r)")
	public List<Chorbi> getChorbiesWithNoLikes();

	//Dashboard - 16
	@Query("select c, avg(r.stars) from Likes r, Chorbi c where c=r.liked group by c order by avg(r.stars) desc")
	public List<Object[]> getChorbiesWithAvgStarsOrderedByAvgStars();

}
