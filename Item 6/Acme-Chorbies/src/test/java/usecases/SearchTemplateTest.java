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

import services.SearchTemplateService;
import utilities.AbstractTest;
import domain.SearchTemplate;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SearchTemplateTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private SearchTemplateService	searchTemplateService;


	// Tests ------------------------------------------------------------------

	//Caso de uso de editar un banner:
	//Test positivo todo opcional
	@Test
	public void editSearchTemplateTest1() {
		this.templateEditSearchTemplate("chorbi1", "searchTemplateChorbi1", "", 0, "", "", null);
	}
	//Test positivo con desiredRelationship=activities
	@Test
	public void editSearchTemplateTest2() {
		this.templateEditSearchTemplate("chorbi1", "searchTemplateChorbi1", "activities", 0, "", "", null);
	}
	//Test positivo con desiredRelationship=friendship
	@Test
	public void editSearchTemplateTest3() {
		this.templateEditSearchTemplate("chorbi1", "searchTemplateChorbi1", "friendship", 0, "", "", null);
	}
	//Test positivo con desiredRelationship=love
	@Test
	public void editSearchTemplateTest4() {
		this.templateEditSearchTemplate("chorbi1", "searchTemplateChorbi1", "love", 0, "", "", null);
	}
	//Test positivo con edad
	@Test
	public void editSearchTemplateTest5() {
		this.templateEditSearchTemplate("chorbi1", "searchTemplateChorbi1", "", 25, "", "", null);
	}
	//Test positivo con edad y desiredRelathionship
	@Test
	public void editSearchTemplateTest6() {
		this.templateEditSearchTemplate("chorbi1", "searchTemplateChorbi1", "love", 25, "", "", null);
	}
	//Test positivo con genre=man
	@Test
	public void editSearchTemplateTest7() {
		this.templateEditSearchTemplate("chorbi1", "searchTemplateChorbi1", "", 0, "man", "", null);
	}

	//Test positivo con genre=woman
	@Test
	public void editSearchTemplateTest8() {
		this.templateEditSearchTemplate("chorbi1", "searchTemplateChorbi1", "", 0, "woman", "", null);
	}
	//Test positivo con genre y edad
	@Test
	public void editSearchTemplateTest9() {
		this.templateEditSearchTemplate("chorbi1", "searchTemplateChorbi1", "", 25, "woman", "", null);
	}
	//Test positivo con genre desiredRelathionship
	@Test
	public void editSearchTemplateTest10() {
		this.templateEditSearchTemplate("chorbi1", "searchTemplateChorbi1", "love", 0, "woman", "", null);
	}
	//Test positivo con genre desiredRelathionship y edad
	@Test
	public void editSearchTemplateTest11() {
		this.templateEditSearchTemplate("chorbi1", "searchTemplateChorbi1", "love", 25, "woman", "", null);
	}
	//Test positivo con keyword
	@Test
	public void editSearchTemplateTest12() {
		this.templateEditSearchTemplate("chorbi1", "searchTemplateChorbi1", "love", 25, "woman", "a", null);
	}
	//Test Negativo->sin loguearse
	@Test
	public void editSearchTemplateTest13() {
		this.templateEditSearchTemplate(null, "searchTemplateChorbi1", "love", 25, "woman", "a", IllegalArgumentException.class);

	}
	//Test Negativo->Logueado como admin
	@Test
	public void editSearchTemplateTest14() {
		this.templateEditSearchTemplate("admin", "searchTemplateChorbi1", "love", 25, "woman", "a", IllegalArgumentException.class);

	}
	//Test Negativo->Editar el SearchTemplate de otro
	@Test
	public void editSearchTemplateTest15() {
		this.templateEditSearchTemplate("chorbi2", "searchTemplateChorbi1", "love", 25, "woman", "a", IllegalArgumentException.class);

	}
	//Test Negativo->desiredRelathionship fuera del pattern
	@Test
	public void editSearchTemplateTest16() {
		this.templateEditSearchTemplate("chorbi1", "searchTemplateChorbi1", "EstoDebeFallar", 25, "woman", "a", ConstraintViolationException.class);

	}
	//Test Negativo->edad negativa
	@Test
	public void editSearchTemplateTest17() {
		this.templateEditSearchTemplate("chorbi1", "searchTemplateChorbi1", "love", -25, "woman", "a", ConstraintViolationException.class);
	}
	//Test Negativo->genre fuera del pattern
	@Test
	public void editSearchTemplateTest18() {
		this.templateEditSearchTemplate("chorbi1", "searchTemplateChorbi1", "love", 25, "EstoDebeFallar", "a", ConstraintViolationException.class);
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateEditSearchTemplate(final String username, final String searchTemplateBeanName, final String desiredRelationship, final Integer age, final String genre, final String keyword, final Class<?> expected) {
		Class<?> caught;
		SearchTemplate searchTemplate;

		caught = null;
		try {
			this.authenticate(username);

			searchTemplate = this.searchTemplateService.findOne(this.extract(searchTemplateBeanName));

			searchTemplate.setDesiredRelationship(desiredRelationship);
			searchTemplate.setAge(age);
			searchTemplate.setGenre(genre);
			searchTemplate.setKeyword(keyword);

			this.searchTemplateService.save(searchTemplate);
			this.searchTemplateService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
