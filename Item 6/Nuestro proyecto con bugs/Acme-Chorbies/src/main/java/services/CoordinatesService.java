
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CoordinatesRepository;
import domain.Coordinates;

@Service
@Transactional
public class CoordinatesService {

	//Managed Repository--------------------------------------------------------------------

	@Autowired
	private CoordinatesRepository	coordinatesRepository;


	// Supporting Services --------------------------------------

	//Simple CRUD methods-------------------------------------------------------------------

	public Coordinates create() {
		final Coordinates result = new Coordinates();
		result.setCity("");
		result.setCountry("");
		result.setProvince("");
		result.setState("");
		return result;
	}

	public Coordinates save(final Coordinates coordinates) {
		Coordinates result;

		Assert.notNull(coordinates, "coordinates.error.null");

		//		Assert.isTrue(!coordinates.getCity().isEmpty(), "coordinates.error.empty.city");

		result = this.coordinatesRepository.save(coordinates);

		Assert.notNull(result, "coordinates.error.commit");

		return result;

	}

	public void delete(final Coordinates coordinates) {

		this.coordinatesRepository.delete(coordinates);

	}

	//Other Business methods-------------------------------------------------------------------

}
