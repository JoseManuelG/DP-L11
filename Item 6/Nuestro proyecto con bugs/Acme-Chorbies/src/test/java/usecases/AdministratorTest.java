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

import services.ChorbiService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdministratorTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ChorbiService	chorbiService;


	// Tests ------------------------------------------------------------------

	//Caso de uso añadir a cada churri la cuota definida en configuration si tiene creditCard:
	//test positivo
	@Test
	public void editConfigurationTest1() {
		this.templateUpdateChorbiChargedFees("admin", null);
	}
	//sin loguearse
	@Test
	public void editConfigurationTest2() {
		this.templateUpdateChorbiChargedFees(null, IllegalArgumentException.class);
	}
	//no logeado como admin
	@Test
	public void editConfigurationTest3() {
		this.templateUpdateChorbiChargedFees("chorbi1", IllegalArgumentException.class);
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateUpdateChorbiChargedFees(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			this.chorbiService.updateChorbiesChargedFees();

			this.chorbiService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
