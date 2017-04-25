
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Register;

@Repository
public interface RegisterRepository extends JpaRepository<Register, Integer> {

	@Query("select count(r) from Register r where r.event.id = ?1")
	public Integer getNumberOfChorbiesForEvent(int eventId);

	@Query("select r from Register r where r.event.id = ?1 and r.chorbi.id = ?2")
	public Register findByEventAndChorbi(int eventId, int chorbiId);
	@Query("select r from Register r where r.chorbi.id = ?1")
	public Collection<Register> findAllFromChorbi(int chorbiId);

}
