
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ChirpRepository;
import domain.Attachment;
import domain.Chirp;
import domain.Chorbi;
import forms.ChirpForm;

@Service
@Transactional
public class ChirpService {

	//Managed Repository--------------------------------------------------------------------
	@Autowired
	private ChirpRepository		chirpRepository;

	//Supported Services--------------------------------------------------------------------
	@Autowired
	private ChorbiService		chorbiService;
	@Autowired
	private AttachmentService	attachmentService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods------------------------------------------------------------------
	public Chirp create(final int recipientId) {
		final Chirp result = new Chirp();
		final Chorbi recipient;

		recipient = this.chorbiService.findOne(recipientId);
		Assert.notNull(recipient);
		result.setRecipient(recipient);
		result.setRecipientName(recipient.getName());
		final Chorbi sender = this.chorbiService.findChorbiByPrincipal();
		result.setSender(sender);
		result.setSenderName(sender.getName());
		result.setSendingMoment(new Date(System.currentTimeMillis() - 100));

		result.setIsSender(false);
		return result;
	}

	public Chirp findOne(final int chirpId) {

		Assert.notNull(chirpId, "No Puedes Encontrar un mensaje sin ID");
		Assert.isTrue(chirpId >= 0, "La Id no es valida");

		final Chirp result = this.chirpRepository.findOne(chirpId);
		//añadido  assert para comprobar que el mensaje es suyo,
		//su copia ya sea la de enviado cuando eres el que envia o al recibido
		//el findOne se usa en view, reply, forward... etc asi que cubre todo

		Assert.notNull(result);

		if (result.getSender() == null && result.getRecipient() != null)
			Assert.isTrue(result.getRecipient().equals(this.chorbiService.findChorbiByPrincipal()) && !result.getIsSender());
		else if (result.getSender() != null && result.getRecipient() == null)
			Assert.isTrue(result.getSender().equals(this.chorbiService.findChorbiByPrincipal()) && result.getIsSender());
		else
			Assert.isTrue((result.getSender().equals(this.chorbiService.findChorbiByPrincipal()) && result.getIsSender()) || result.getRecipient().equals(this.chorbiService.findChorbiByPrincipal()) && !result.getIsSender());

		return result;
	}

	public Chirp save(final Chirp chirp, final Collection<Attachment> attachments) {
		Chirp result;
		Chirp copyChirp;
		Chirp savedCopyChirp;

		Assert.notNull(chirp.getRecipient(), "El mensaje debe tener un destinatario");

		Assert.notNull(chirp.getSender(), "El mensaje debe tener un remitente");

		final Chorbi sender = this.chorbiService.findChorbiByPrincipal();

		Assert.isTrue(sender.equals(chirp.getSender()), "El remitente debe ser el mismo que esta conectado");
		Assert.isTrue(chirp.getId() == 0, "No puedes editar un mensaje");

		// Creamos copia del mensaje en un segundo mensaje;

		copyChirp = this.copyChirp(chirp);
		//Se almacena el mensaje original y sus attachments
		result = this.chirpRepository.save(chirp);
		this.attachmentService.addAttachments(attachments, result);
		//Se almacena el mensaje copia y sus attachments
		savedCopyChirp = this.chirpRepository.save(copyChirp);
		this.attachmentService.addAttachments(attachments, savedCopyChirp);
		return result;
	}
	//Simplemente crea un mensaje nuevo y le settea todos los datos del mensaje de entrada menos el isSender.
	private Chirp copyChirp(final Chirp chirp) {
		final Chirp result = new Chirp();

		result.setRecipient(chirp.getRecipient());
		result.setRecipientName(chirp.getRecipient().getName());

		result.setSender(chirp.getSender());
		result.setSenderName(chirp.getSender().getName());
		result.setText(chirp.getText());
		result.setSubject(chirp.getSubject());
		result.setSendingMoment(chirp.getSendingMoment());
		result.setIsSender(true);

		return result;
	}

	public void delete(final int chirpId) {

		//en vez de pasar chirp pasas la id para no tocar en el controlador, y en el findOne
		//ya se hacen los asserts
		final Chirp chirp = this.findOne(chirpId);

		Assert.notNull(chirp);

		this.attachmentService.deleteAttachmentsOfChirp(chirp);
		this.chirpRepository.delete(chirp);

	}
	public void delete(final Collection<Chirp> Chirps) {
		this.delete(Chirps);
	}
	public void flush() {
		this.chirpRepository.flush();
	}

	//Other Bussnisnes methods------------------------------------------------------------
	//Devuelve los mensajes que ha enviado el chorbi
	public List<Chirp> findSentChirpOfPrincipal() {
		final int senderId = this.chorbiService.findChorbiByPrincipal().getId();
		final List<Chirp> result = this.chirpRepository.findSentChirpOfChorbi(senderId);
		return result;
	}

	//Devuelve los mensajes que ha recibido el chorbi	
	public List<Chirp> findReceivedChirpOfPrincipal() {
		final int recipientId = this.chorbiService.findChorbiByPrincipal().getId();
		final List<Chirp> result = this.chirpRepository.findReceivedChirpOfChorbi(recipientId);
		return result;
	}

	public Chirp reconstruct(final ChirpForm chirpForm, final BindingResult binding) {
		final Chirp result = this.create(chirpForm.getRecipient().getId());
		result.setText(chirpForm.getText());
		result.setSubject(chirpForm.getSubject());
		this.validator.validate(result, binding);

		if (!binding.hasErrors())
			for (final Attachment a : chirpForm.getAttachments()) {
				a.setChirp(result);
				this.validator.validate(a, binding);
			}
		return result;

	}

	//Basicamente te hace el ChirpForm relleno del mensaje que has pasado, luego en la vista seleccionarias a quien mandarselo 
	//y despues pasarias el mensaje al save
	public ChirpForm forwardChirp(final int chirpId) {
		//Lo he cambiado para que pida chirpId en vez de chirp para no tener que 
		//hacer en controlador cosas de servicios 
		final Chirp chirp = this.findOne(chirpId);
		final ChirpForm result = new ChirpForm();
		final LinkedList<Attachment> attachments = new LinkedList<Attachment>();
		result.setAction(2);
		attachments.addAll(this.attachmentService.copyAttachments(chirp));
		result.setText(chirp.getText());
		result.setSubject(chirp.getSubject());
		result.setAttachments(attachments);
		return result;
	}
	//Para responder el mensaje, 
	public ChirpForm replyChirp(final int chirpId) {
		//Lo he cambiado para que pida chirpId en vez de chorbiId para no tener que 
		//hacer en controlador cosas de servicios
		final ChirpForm result = new ChirpForm();
		result.setAction(1);
		final Chirp chirp = this.findOne(chirpId);
		Assert.notNull(chirp.getSender());
		final Chorbi recipient = this.chorbiService.findOne(chirp.getSender().getId());
		result.setRecipient(recipient);
		return result;
	}

	public void deleteFromChorbi(final Chorbi chorbi) {
		final Collection<Chirp> chirps;
		Collection<Chirp> chirps2;

		chirps = new ArrayList<Chirp>();
		chirps2 = new ArrayList<Chirp>();
		chirps.addAll(this.chirpRepository.findSentChirpOfChorbi(chorbi.getId()));
		chirps.addAll(this.chirpRepository.findReceivedChirpOfChorbi(chorbi.getId()));

		this.attachmentService.deleteChorbi(chorbi);
		this.chirpRepository.delete(chirps);
		chirps.clear();
		chirps2.addAll(this.chirpRepository.findReceivedChirpOfChorbi2(chorbi.getId()));
		for (final Chirp chirp : chirps2) {
			chirp.setRecipient(null);
			chirps.add(chirp);
		}
		//Vaciado de la colleción para pasar al segundo for
		chirps2.clear();
		//------------------------------------------------
		chirps2.addAll(this.chirpRepository.findSentChirpOfChorbi2(chorbi.getId()));
		for (final Chirp chirp : chirps2) {
			chirp.setSender(null);
			chirps.add(chirp);
		}
		this.chirpRepository.save(chirps);
	}

}
