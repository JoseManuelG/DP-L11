
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.RegisterRepository;
import domain.Register;

@Service
@Transactional
public class RegisterService {

	//Managed Repository--------------------------------------------------------------------
	@Autowired
	private RegisterRepository	registerRepository;


	//Supported Services--------------------------------------------------------------------

	//Simple CRUD methods------------------------------------------------------------------

	//Other Bussnisnes methods------------------------------------------------------------

	public Integer getNumberOfChorbiesForEvent(final int eventId) {
		return this.registerRepository.getNumberOfChorbiesForEvent(eventId);
	}

	public Register findByEventAndChorbi(final int eventId, final int chorbiId) {
		return this.registerRepository.findByEventAndChorbi(eventId, chorbiId);
	}

}
