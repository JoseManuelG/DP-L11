
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.AdministratorRepository;
import security.LoginService;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	// Managed Repository --------------------------------------
	@Autowired
	private AdministratorRepository	administratorRepository;


	// Supporting Services --------------------------------------

	//Simple CRUD methods-------------------------------------------------------------------

	// other business methods --------------------------------------

	public Administrator findAdministratorByPrincipal() {
		Administrator result;
		result = this.administratorRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return result;
	}

}
