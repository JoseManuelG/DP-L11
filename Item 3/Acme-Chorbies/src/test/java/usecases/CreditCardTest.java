/*
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.CreditCardService;
import services.CustomerService;
import utilities.AbstractTest;
import domain.CreditCard;
import domain.Customer;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CreditCardTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private CustomerService		customerService;


	// Tests ------------------------------------------------------------------

	//Caso de uso registrar una credit card
	//test positivo
	@Test
	public void createCreditCardTest1() {
		this.templateCreateCreditCard("chorbi4", "0", "VISA", 500, "test", 10, 2050, null);
	}
	//sin loguearse
	@Test
	public void createCreditCardTest2() {
		this.templateCreateCreditCard(null, "0", "VISA", 500, "test", 10, 2050, IllegalArgumentException.class);
	}
	//numero incorrecto
	@Test
	public void createCreditCardTest3() {
		this.templateCreateCreditCard(null, "04515", "VISA", 500, "test", 10, 2050, IllegalArgumentException.class);
	}
	//brandName incorrecta
	@Test
	public void createCreditCardTest4() {
		this.templateCreateCreditCard(null, "0", "error", 500, "test", 10, 2050, IllegalArgumentException.class);
	}
	//holderName incorrecto
	@Test
	public void createCreditCardTest5() {
		this.templateCreateCreditCard(null, "0", "VISA", 500, "", 10, 2050, IllegalArgumentException.class);
	}
	//mes incorrecto
	@Test
	public void createCreditCardTest6() {
		this.templateCreateCreditCard(null, "0", "VISA", 500, "test", -10, 2050, IllegalArgumentException.class);
	}
	//año incorrecto
	@Test
	public void createCreditCardTest7() {
		this.templateCreateCreditCard(null, "0", "VISA", 500, "test", 10, -2050, IllegalArgumentException.class);
	}
	//fecha pasada
	@Test
	public void createCreditCardTest8() {
		this.templateCreateCreditCard(null, "0", "VISA", 500, "test", 10, 2010, IllegalArgumentException.class);
	}
	// Ancillary methods ------------------------------------------------------

	protected void templateCreateCreditCard(final String username, final String number, final String brandName, final int cvvCode, final String holderName, final int expirationMonth, final int expirationYear, final Class<?> expected) {
		Class<?> caught;
		CreditCard creditCard;
		Customer principal;

		caught = null;
		try {
			this.authenticate(username);

			principal = this.customerService.findCustomerByPrincipal();
			creditCard = this.creditCardService.create();

			creditCard.setBrandName(brandName);
			creditCard.setCustomer(principal);
			creditCard.setCvvCode(cvvCode);
			creditCard.setExpirationMonth(expirationMonth);
			creditCard.setExpirationYear(expirationYear);
			creditCard.setHolderName(holderName);
			creditCard.setNumber(number);

			this.creditCardService.save(creditCard);

			this.creditCardService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
