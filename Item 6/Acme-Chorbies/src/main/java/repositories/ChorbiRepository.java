
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chorbi;
import domain.Coordinates;

@Repository
public interface ChorbiRepository extends JpaRepository<Chorbi, Integer> {

	@Query("select c from Chorbi c where c.userAccount.id = ?1")
	public Chorbi findByUserAccountId(int id);

	@Query("select c from Chorbi c where c.desiredRelationship like concat('%',?1,'%') and c.genre like concat(?2) and c.description like concat('%', ?3, '%') "
		+ "and c.coordinates.city like concat('%', ?4, '%')  and c.coordinates.province like concat('%', ?5, '%') and c.coordinates.country like concat('%', ?6, '%') and c.coordinates.state like concat('%', ?7, '%') and ?8 <=c.birthDate and ?9 >= c.birthDate")
	public Collection<Chorbi> searchChorbis(String desiredRelathionship, String genre, String keyword, String cityCoordinate, String provinceCoordinate, String countryCoordinate, String stateCoordinate, Date firstDate, Date SecondDate);

	@Query("select c from Chorbi c where c.desiredRelationship like concat('%',?1,'%') and c.genre like concat(?2) and c.description like concat('%', ?3, '%') "
		+ "and c.coordinates.city like concat('%', ?4, '%')  and c.coordinates.province like concat('%', ?5, '%') and c.coordinates.country like concat('%', ?6, '%') and c.coordinates.state like concat('%', ?7, '%') ")
	public Collection<Chorbi> searchChorbisWithoutAge(String desiredRelathionship, String genre, String keyword, String cityCoordinate, String provinceCoordinate, String countryCoordinate, String stateCoordinate);

	@Query("select c from Chorbi c where c.desiredRelationship like concat('%',?1,'%') and c.description like concat('%', ?2, '%') "
		+ "and c.coordinates.city like concat('%', ?3, '%')  and c.coordinates.province like concat('%', ?4, '%') and c.coordinates.country like concat('%', ?5, '%') and c.coordinates.state like concat('%', ?6, '%') and ?7 <=c.birthDate and ?8 >= c.birthDate")
	public Collection<Chorbi> searchChorbisWithOutGenre(String desiredRelathionship, String keyword, String cityCoordinate, String provinceCoordinate, String countryCoordinate, String stateCoordinate, Date firstDate, Date SecondDate);

	@Query("select c from Chorbi c where c.desiredRelationship like concat('%',?1,'%') and c.description like concat('%', ?2, '%') "
		+ "and c.coordinates.city like concat('%', ?3, '%')  and c.coordinates.province like concat('%', ?4, '%') and c.coordinates.country like concat('%', ?5, '%') and c.coordinates.state like concat('%', ?6, '%')")
	public Collection<Chorbi> searchChorbisWithoutAgeAndGenre(String desiredRelathionship, String keyword, String cityCoordinate, String provinceCoordinate, String countryCoordinate, String stateCoordinate);

	@Query("select c.coordinates from Chorbi c where c.userAccount.id = ?1")
	public Coordinates findCoordinatesByUserAccountId(int id);

	@Query("select c.customer from CreditCard c where c in (select c2 from CreditCard c2 where c2.customer.class = Chorbi)")
	public Collection<Chorbi> getChorbiesWithCC();
}
