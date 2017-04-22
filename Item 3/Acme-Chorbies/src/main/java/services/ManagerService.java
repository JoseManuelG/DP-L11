
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ManagerRepository;
import security.LoginService;
import domain.Manager;

@Service
@Transactional
public class ManagerService {

	//Managed Repository--------------------------------------------------------------------

	@Autowired
	private ManagerRepository	managerRepository;


	// Supporting Services --------------------------------------

	//Simple CRUD methods-------------------------------------------------------------------

	//Other Business methods-------------------------------------------------------------------

	public Manager findManagerByPrincipal() {
		Manager result;
		result = this.managerRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return result;
	}
}
