
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import security.UserAccount;
import security.UserAccountRepository;

@Service
@Transactional
public class UserAccountService {

	//Managed Repository--------------------------------------------------------------------

	@Autowired
	private UserAccountRepository	userAccountRepository;


	// Supporting Services --------------------------------------

	//Simple CRUD methods-------------------------------------------------------------------

	public UserAccount save(final UserAccount userAccount) {
		UserAccount result;

		result = this.userAccountRepository.save(userAccount);

		return result;

	}

	public void delete(final int userAccountId) {

		this.userAccountRepository.delete(userAccountId);

	}

	//Other Business methods-------------------------------------------------------------------

}
