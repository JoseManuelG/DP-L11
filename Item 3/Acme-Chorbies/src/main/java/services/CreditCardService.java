
package services;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
	private ChorbiService			chorbiService;

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
		Assert.notNull(creditCard, "La tarjeta de crédito no puede ser nula");
		CreditCard result;

		if (creditCard.getId() == 0)
			creditCard.setCustomer(this.customerService.findCustomerByPrincipal());
		else
			this.checkPrincipal(creditCard);

		result = this.creditCardRepository.save(creditCard);
		return result;
	}

	public void delete(final CreditCard creditCard) {

		Assert.notNull(creditCard);
		this.creditCardRepository.delete(creditCard);
		Assert.isTrue(!this.creditCardRepository.exists(creditCard.getId()));
	}

	public void deleteFromChorbi(final Chorbi chorbi) {
		CreditCard creditCard;

		creditCard = this.getCreditCardByChorbi(chorbi);
		if (creditCard != null)
			this.creditCardRepository.delete(creditCard);
	}

	// Other Bussiness Methods --------------------------------------------------------

	public CreditCard getCreditCardByChorbi() {
		CreditCard creditCard;
		Chorbi chorbi;
		chorbi = this.chorbiService.findChorbiByPrincipal();
		creditCard = this.creditCardRepository.findCreditCardByChorbiId(chorbi.getId());
		return creditCard;
	}

	public CreditCard getCreditCardByChorbi(final Chorbi chorbi) {
		CreditCard creditCard;

		creditCard = this.creditCardRepository.findCreditCardByChorbiId(chorbi.getId());

		return creditCard;
	}

	public boolean checkCreditCardByChorbi() {
		CreditCard creditCard;
		LocalDate expireTime, now;
		Period period;
		Boolean result;

		creditCard = this.getCreditCardByChorbi();
		//Añadido el bloque if por Roldan, porque si tenia una tarjeta de credito que caducaba el mes 12,  fallaba
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

}
