
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
		Assert.notNull(creditCard, "La tarjeta de crédito no puede ser nula");
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
	public void flush() {
		this.creditCardRepository.flush();
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
		boolean result;

		creditCard = this.getCreditCardByPrincipal();
		if (creditCard == null)
			result = false;
		else
			result = this.checkCreditCard(creditCard);

		return result;
	}

	public boolean checkCreditCard(final CreditCard creditCard) {
		LocalDate expireTime, now;
		Period period;
		Boolean result;

		//Añadido el bloque if por Roldan, porque si tenia una tarjeta de credito que caducaba el mes 12,  fallaba
		if (creditCard.getExpirationMonth() < 12)
			expireTime = new LocalDate(creditCard.getExpirationYear(), creditCard.getExpirationMonth() + 1, 1);
		else
			expireTime = new LocalDate(creditCard.getExpirationYear() + 1, 1, 1);
		now = new LocalDate();
		//Cambio realizado porque el period devolvia una estructura de [años,meses,dias], pero solo se miraba el día, por lo que si salia un año y 1 dia, 
		//saltaba que estaba mal. Se ha solucionado pidiendo que te devuelva un estructura de [dias], por lo que no deberia haber problema de esta clase.
		//Cambio esta linea:
		//period = new Period(now, expireTime, PeriodType.yearMonthDay());
		//Por esta:
		period = new Period(now, expireTime, PeriodType.days());

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
			result.setId(old.getId());
			result.setVersion(old.getVersion());
		} else
			creditCard.setCustomer(this.customerService.findCustomerByPrincipal());

		result.setBrandName(creditCard.getBrandName());
		result.setCvvCode(creditCard.getCvvCode());
		result.setExpirationMonth(creditCard.getExpirationMonth());
		result.setExpirationYear(creditCard.getExpirationYear());
		result.setHolderName(creditCard.getHolderName());
		result.setNumber(creditCard.getNumber());

		return result;
	}

}
