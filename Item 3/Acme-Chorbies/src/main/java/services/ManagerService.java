
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ManagerRepository;
import domain.Manager;

@Service
@Transactional
public class ManagerService {

	// Managed Repository --------------------------------------
	@Autowired
	private ManagerRepository	managerRepository;


	// Supporting Services --------------------------------------

	//Simple CRUD methods-------------------------------------------------------------------

	public Collection<Manager> findAll() {
		return this.managerRepository.findAll();
	}

	// other business methods --------------------------------------

}
