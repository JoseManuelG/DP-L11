
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CustomerRepository;
import security.LoginService;
import domain.Actor;
import domain.Customer;
import forms.ActorForm;

@Service
@Transactional
public class CustomerService {

	// Managed Repository --------------------------------------
	@Autowired
	private CustomerRepository	customerRepository;

	// Supporting Services --------------------------------------

	@Autowired
	private ActorService		actorService;


	//Simple CRUD methods-------------------------------------------------------------------

	public Customer findOne(final int recipientId) {
		return this.customerRepository.findOne(recipientId);
	}

	public Collection<Customer> findAll() {
		return this.customerRepository.findAll();
	}

	//Other business methods --------------------------------------

	public Customer findCustomerByPrincipal() {
		Customer result;
		result = this.customerRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return result;
	}

	public void setCustomerProperties(final Customer customer) {

		this.actorService.setActorProperties(customer);
		customer.setChargedFee(0);
	}

	public void setReconstructCustomerProperties(final Customer result, final Customer origin, final ActorForm actorForm) {

		this.actorService.setReconstructActorProperties(result, origin, actorForm);
		result.setChargedFee(origin.getChargedFee());

	}

	public void setReconstructNewCustomerProperties(final Actor result, final ActorForm actorForm) {

		this.actorService.setReconstructNewActorProperties(result, actorForm);

	}

}
