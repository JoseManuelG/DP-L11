
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import domain.Manager;
import forms.ManagerForm;

@Service
@Transactional
public class ManagerService {

	//Managed Repository--------------------------------------------------------------------

	@Autowired
	private ManagerRepository	managerRepository;

	// Supporting Services --------------------------------------

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods-------------------------------------------------------------------

	public Manager create() {
		Manager result;
		Authority authority;

		result = new Manager();
		this.customerService.setCustomerProperties(result);

		authority = new Authority();
		authority.setAuthority("MANAGER");

		result.getUserAccount().addAuthority(authority);

		result.setCompany("");
		result.setVAT("");

		return result;
	}

	public Manager findOne(final int managerId) {
		return this.managerRepository.findOne(managerId);
	}

	public Collection<Manager> findAll() {
		Collection<Manager> result;
		result = this.managerRepository.findAll();
		return result;
	}

	public Manager save(final Manager manager) {
		Manager result;

		Assert.notNull(manager, "manager.error.null");

		manager.setUserAccount(this.userAccountService.save(manager.getUserAccount()));
		result = this.managerRepository.save(manager);

		Assert.notNull(result, "manager.error.commit");

		return result;
	}

	public void delete() {

		this.findManagerByPrincipal();
		//TODO Si se hace borrado de manager hacer aqui llamada a borrado de todos sus eventos.

	}

	//Other Business methods-------------------------------------------------------------------

	public Manager findManagerByPrincipal() {
		Manager result;
		result = this.managerRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return result;
	}

	public Manager reconstructNewManager(final ManagerForm managerForm, final BindingResult binding) {
		Md5PasswordEncoder encoder;
		Manager result;

		result = this.create();
		encoder = new Md5PasswordEncoder();

		this.customerService.setReconstructNewCustomerProperties(result, managerForm);
		this.setReconstructProperties(result, managerForm);

		this.validator.validate(result, binding);
		result.getUserAccount().setPassword(encoder.encodePassword(managerForm.getUserAccount().getPassword(), null));
		return result;
	}

	public Manager reconstruct(final ManagerForm managerForm, final Manager manager, final BindingResult binding) {
		Md5PasswordEncoder encoder;
		Manager result;

		result = this.create();
		encoder = new Md5PasswordEncoder();

		this.customerService.setReconstructCustomerProperties(result, manager, managerForm);
		this.setReconstructProperties(result, managerForm);

		this.validator.validate(result, binding);

		result.getUserAccount().setPassword(encoder.encodePassword(managerForm.getUserAccount().getPassword(), null));

		return result;
	}

	private void setReconstructProperties(final Manager result, final ManagerForm managerForm) {

		result.setCompany(managerForm.getCompany());
		result.setVAT(managerForm.getVAT());

	}

	public void flush() {
		this.managerRepository.flush();
	}

}
