
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.SearchTemplate;

@Repository
public interface SearchTemplateRepository extends JpaRepository<SearchTemplate, Integer> {

	@Query("select s from SearchTemplate s where s.chorbi.id=?1")
	public SearchTemplate findByChrobi(int chorbiId);

	@Query("select s from SearchTemplate s where ?1 member of s.chorbies")
	public Collection<SearchTemplate> findAllWithChorbi(int id);

}
