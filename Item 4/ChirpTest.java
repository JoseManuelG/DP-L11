/*
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package usecases;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ChirpService;
import utilities.AbstractTest;
import domain.Attachment;
import domain.Chirp;
import forms.ChirpForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ChirpTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ChirpService	chirpService;


	// Tests ------------------------------------------------------------------

	//Caso de uso de escribir un mensaje:
	//test positivo
	@Test
	public void WriteChirpTest1() {
		this.templateWriteChirp("chorbi1", 1096, "test subject", "test text", null);
	}
	//sin loguearse
	@Test
	public void WriteChirpTest2() {
		this.templateWriteChirp(null, 1096, "test subject", "test text", IllegalArgumentException.class);
	}
	//recipient no válido
	@Test
	public void WriteChirpTest3() {
		this.templateWriteChirp("chorbi1", 1, "test subject", "test text", IllegalArgumentException.class);
	}
	//subject blank
	@Test
	public void WriteChirpTest4() {
		this.templateWriteChirp("chorbi1", 1096, "", "test text", ConstraintViolationException.class);
	}
	//text blank
	@Test
	public void WriteChirpTest5() {
		this.templateWriteChirp("chorbi1", 1096, "test subject", "", ConstraintViolationException.class);
	}

	//	@Test
	//	public void driverWriteChirp() {
	//		final Object testingData[][] = {
	//			{
	//				"chorbi1", 994, "test subject", "test text", null
	//			}, {
	//				null, 994, "test subject", "test text", IllegalArgumentException.class
	//			}, {
	//				"chorbi1", 1, "test subject", "test text", IllegalArgumentException.class
	//			}, {
	//				"chorbi1", 994, "", "test text", ConstraintViolationException.class
	//			}, {
	//				"chorbi1", 994, "test subject", "", ConstraintViolationException.class
	//			}
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.templateWriteChirp((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	//	}

	//Caso de uso de responder a un mensaje:
	//test positivo
	@Test
	public void ReplyChirpTest1() {
		this.templateReplyChirp("chorbi1", 1039, "test subject", "test text", null);
	}
	//mensaje sin sender(cuenta borrada del sender)
	@Test
	public void ReplyChirpTest2() {
		this.templateReplyChirp("chorbi1", 1056, "test subject", "test text", IllegalArgumentException.class);
	}
	//mensaje que no es suyo
	@Test
	public void ReplyChirpTest3() {
		this.templateReplyChirp("chorbi1", 1055, "test subject", "test text", IllegalArgumentException.class);
	}
	//sin loguearse
	@Test
	public void ReplyChirpTest4() {
		this.templateReplyChirp(null, 1039, "test subject", "test text", IllegalArgumentException.class);
	}
	//titulo blank
	@Test
	public void ReplyChirpTest5() {
		this.templateReplyChirp("chorbi1", 1039, "", "test text", ConstraintViolationException.class);
	}
	//texto blank
	@Test
	public void ReplyChirpTest6() {
		this.templateReplyChirp("chorbi1", 1039, "test subject", "", ConstraintViolationException.class);
	}

	//	@Test
	//	public void driveReplyChirp() {
	//		final Object testingData[][] = {
	//			{
	//				"admin", 564, "test subject", "test text", null
	//			}, {
	//				"admin", 587, "test subject", "test text", IllegalArgumentException.class
	//			}, {
	//				"admin", 605, "test subject", "test text", IllegalArgumentException.class
	//			}, {
	//				null, 564, "test subject", "test text", IllegalArgumentException.class
	//			}, {
	//				"admin", 564, "", "test text", ConstraintViolationException.class
	//			}, {
	//				"admin", 564, "test subject", "", ConstraintViolationException.class
	//			}
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.templateReplyChirp((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	//	}

	//Caso de uso de reenviar un mensaje:
	//test positivo
	@Test
	public void ForwardChirpTest1() {
		this.templateForwardChirp("chorbi1", 1039, 1097, null);
	}
	//sin loguearse
	@Test
	public void ForwardChirpTest2() {
		this.templateForwardChirp(null, 1039, 1097, IllegalArgumentException.class);
	}
	//mensaje que no es suyo
	@Test
	public void ForwardChirpTest3() {
		this.templateForwardChirp("chorbi5", 1039, 1097, IllegalArgumentException.class);
	}
	//recipient no válido
	@Test
	public void ForwardChirpTest4() {
		this.templateForwardChirp("chorbi1", 1039, 1, IllegalArgumentException.class);
	}

	//	@Test
	//	public void driveForwardChirp() {
	//		final Object testingData[][] = {
	//			{
	//				"admin", 564, 993, null
	//			}, {
	//				null, 564, 993, IllegalArgumentException.class
	//			}, {
	//				"admin", 605, 993, IllegalArgumentException.class
	//			}, {
	//				"admin", 564, 1, IllegalArgumentException.class
	//			}
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.templateForwardChirp((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
	//	}

	//Caso de uso de borrar un mensaje:
	//test positivo
	@Test
	public void DeleteChirpTest1() {
		this.templateDeleteChirp("chorbi1", 1039, null);
	}
	//sin loguearse
	@Test
	public void DeleteChirpTest2() {
		this.templateDeleteChirp(null, 1039, IllegalArgumentException.class);
	}
	//mensaje que no es suyo
	@Test
	public void DeleteChirpTest3() {
		this.templateDeleteChirp("chorbi5", 1039, IllegalArgumentException.class);
	}

	//	@Test
	//	public void driveDeleteChirp() {
	//		final Object testingData[][] = {
	//			{
	//				"admin", 564, null
	//			}, {
	//				null, 564, IllegalArgumentException.class
	//			}, {
	//				"admin", 605, IllegalArgumentException.class
	//			}
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.templateDeleteChirp((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	//	}

	// Ancillary methods ------------------------------------------------------

	protected void templateWriteChirp(final String username, final int recipientId, final String subject, final String text, final Class<?> expected) {
		Class<?> caught;
		Chirp chirp;
		Collection<Attachment> attachments;

		caught = null;
		try {
			this.authenticate(username);
			this.chirpService.findSentChirpOfPrincipal();

			attachments = new ArrayList<Attachment>();
			chirp = this.chirpService.create(recipientId);

			chirp.setSubject(subject);
			chirp.setText(text);

			this.chirpService.save(chirp, attachments);
			this.chirpService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateReplyChirp(final String username, final int chirpId, final String subject, final String text, final Class<?> expected) {
		Class<?> caught;
		Chirp chirp;
		Collection<Attachment> attachments;
		ChirpForm chirpForm;

		caught = null;
		try {
			this.authenticate(username);
			this.chirpService.findReceivedChirpOfPrincipal();

			attachments = new ArrayList<Attachment>();
			chirpForm = this.chirpService.replyChirp(chirpId);
			chirp = this.chirpService.create(chirpForm.getRecipient().getId());

			chirp.setSubject(subject);
			chirp.setText(text);

			this.chirpService.save(chirp, attachments);
			this.chirpService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateForwardChirp(final String username, final int chirpId, final int recipientId, final Class<?> expected) {
		Class<?> caught;
		Chirp chirp;
		Collection<Attachment> attachments;
		ChirpForm chirpForm;
		caught = null;
		try {
			this.authenticate(username);
			this.chirpService.findReceivedChirpOfPrincipal();

			chirpForm = this.chirpService.forwardChirp(chirpId);
			attachments = chirpForm.getAttachments();
			chirp = this.chirpService.create(recipientId);

			chirp.setSubject(chirpForm.getSubject());
			chirp.setText(chirpForm.getText());

			this.chirpService.save(chirp, attachments);
			this.chirpService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateDeleteChirp(final String username, final int chirpId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);
			this.chirpService.findReceivedChirpOfPrincipal();
			this.chirpService.delete(chirpId);

			this.chirpService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
