
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CustomerRepository;
import security.LoginService;
import domain.Customer;

@Service
@Transactional
public class CustomerService {

	// Managed Repository --------------------------------------
	@Autowired
	private CustomerRepository	customerRepository;


	// Supporting Services --------------------------------------

	//Simple CRUD methods-------------------------------------------------------------------

	// other business methods --------------------------------------

	public Customer findCustomerByPrincipal() {
		Customer result;
		result = this.customerRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return result;
	}

	public Customer findOne(final int recipientId) {
		return this.customerRepository.findOne(recipientId);
	}
}
