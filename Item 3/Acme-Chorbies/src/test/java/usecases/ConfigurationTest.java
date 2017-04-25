/*
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package usecases;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ConfigurationService;
import utilities.AbstractTest;
import domain.Configuration;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ConfigurationTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ConfigurationService	configurationService;


	// Tests ------------------------------------------------------------------

	//Caso de uso de editar el tiempo de caché de la configuración del sistema:
	//test positivo
	@Test
	public void editConfigurationTest1() {
		this.templateEditConfiguration("admin", 360000000, 1.0, 1.0, null);
	}
	//sin loguearse
	@Test
	public void editConfigurationTest2() {
		this.templateEditConfiguration(null, 360000000, 1.0, 1.0, IllegalArgumentException.class);
	}
	//no logeado como admin
	@Test
	public void editConfigurationTest3() {
		this.templateEditConfiguration("chorbi1", 360000000, 1.0, 1.0, NullPointerException.class);
	}
	//tiempo negativo
	@Test
	public void editConfigurationTest4() {
		this.templateEditConfiguration("admin", -360000000, 1.0, 1.0, ConstraintViolationException.class);
	}
	//chorbiFee negativa
	@Test
	public void editConfigurationTest5() {
		this.templateEditConfiguration("admin", 360000000, -1.0, 1.0, ConstraintViolationException.class);
	}
	//managerFee negativa
	@Test
	public void editConfigurationTest6() {
		this.templateEditConfiguration("admin", 360000000, 1.0, -1.0, ConstraintViolationException.class);
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateEditConfiguration(final String username, final long cachedTime, final double chorbiFee, final double managerFee, final Class<?> expected) {
		Class<?> caught;
		Configuration configuration;

		caught = null;
		try {
			this.authenticate(username);

			configuration = this.configurationService.findConfiguration();

			configuration.setCachedTime(cachedTime);
			configuration.setChorbiFee(chorbiFee);
			configuration.setManagerFee(managerFee);

			this.configurationService.save(configuration);
			this.configurationService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
