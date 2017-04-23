
package services;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.CreditCardRepository;
import domain.Chorbi;
import domain.CreditCard;
import domain.Customer;

@Service
@Transactional
public class CreditCardService {

	// Managed Repository -------------------------------------------------------------

	@Autowired
	private CreditCardRepository	creditCardRepository;

	// Supporting Services ------------------------------------------------------------

	@Autowired
	private CustomerService			customerService;


	// Constructor --------------------------------------------------------------------

	public CreditCardService() {
		super();
	}

	// Simple CRUD methods ------------------------------------------------------------

	public CreditCard create() {
		CreditCard result;
		Customer customer;

		result = new CreditCard();
		customer = this.customerService.findCustomerByPrincipal();
		result.setCustomer(customer);

		return result;
	}

	public CreditCard save(final CreditCard creditCard) {
		Assert.notNull(creditCard, "La tarjeta de cr�dito no puede ser nula");
		CreditCard result;

		Assert.isTrue(this.checkCreditCard(creditCard));
		this.checkPrincipal(creditCard);

		result = this.creditCardRepository.save(creditCard);
		return result;
	}

	public void delete(final CreditCard creditCard) {

		this.checkPrincipal(creditCard);
		Assert.notNull(creditCard);
		this.creditCardRepository.delete(creditCard);
		Assert.isTrue(!this.creditCardRepository.exists(creditCard.getId()));
	}

	public void deleteFromChorbi(final Chorbi chorbi) {
		CreditCard creditCard;

		creditCard = this.getCreditCardByCustomer(chorbi);
		if (creditCard != null)
			this.creditCardRepository.delete(creditCard);
	}

	// Other Bussiness Methods --------------------------------------------------------

	public CreditCard getCreditCardByPrincipal() {
		CreditCard creditCard;
		Customer customer;
		customer = this.customerService.findCustomerByPrincipal();
		creditCard = this.creditCardRepository.findCreditCardByCustomerId(customer.getId());
		return creditCard;
	}

	public CreditCard getCreditCardByCustomer(final Customer customer) {
		CreditCard creditCard;

		creditCard = this.creditCardRepository.findCreditCardByCustomerId(customer.getId());

		return creditCard;
	}

	public boolean checkCreditCardByPrincipal() {
		CreditCard creditCard;

		creditCard = this.getCreditCardByPrincipal();

		return this.checkCreditCard(creditCard);
	}

	public boolean checkCreditCard(final CreditCard creditCard) {
		LocalDate expireTime, now;
		Period period;
		Boolean result;

		//A�adido el bloque if por Roldan, porque si tenia una tarjeta de credito que caducaba el mes 12,  fallaba
		if (creditCard.getExpirationMonth() < 12)
			expireTime = new LocalDate(creditCard.getExpirationYear(), creditCard.getExpirationMonth() + 1, 1);
		else
			expireTime = new LocalDate(creditCard.getExpirationYear() + 1, 1, 1);
		now = new LocalDate();
		period = new Period(now, expireTime, PeriodType.yearMonthDay());

		result = period.getDays() > 1;

		return result;
	}

	public void checkPrincipal(final CreditCard creditCard) {
		Customer customer;

		customer = this.customerService.findCustomerByPrincipal();
		Assert.isTrue(creditCard.getCustomer().equals(customer));
	}

	public CreditCard reconstruct(final CreditCard creditCard, final BindingResult binding) {
		CreditCard old, result;

		result = this.create();

		if (creditCard.getId() != 0) {
			old = this.creditCardRepository.findOne(creditCard.getId());
			result.setCustomer(old.getCustomer());
		} else
			creditCard.setCustomer(this.customerService.findCustomerByPrincipal());

		result.setBrandName(creditCard.getBrandName());
		result.setCvvCode(creditCard.getCvvCode());
		result.setExpirationMonth(creditCard.getExpirationMonth());
		result.setExpirationYear(creditCard.getExpirationYear());
		result.setHolderName(creditCard.getHolderName());
		result.setId(creditCard.getId());
		result.setNumber(creditCard.getNumber());
		result.setVersion(creditCard.getVersion());

		return result;
	}

}
