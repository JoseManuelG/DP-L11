
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
import domain.Customer;
import domain.Event;
import forms.ChirpBroadcastForm;
import forms.ChirpForm;

@Service
@Transactional
public class ChirpService {

	//Managed Repository--------------------------------------------------------------------
	@Autowired
	private ChirpRepository		chirpRepository;

	//Supported Services--------------------------------------------------------------------
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private AttachmentService	attachmentService;

	@Autowired
	private RegisterService		registerService;

	@Autowired
	private EventService		eventService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods------------------------------------------------------------------
	public Chirp create(final int recipientId) {
		final Chirp result = new Chirp();
		Customer recipient, sender;

		recipient = this.customerService.findOne(recipientId);
		Assert.notNull(recipient);
		result.setRecipient(recipient);
		result.setRecipientName(recipient.getName());
		sender = this.customerService.findCustomerByPrincipal();
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
			Assert.isTrue(result.getRecipient().equals(this.customerService.findCustomerByPrincipal()) && !result.getIsSender());
		else if (result.getSender() != null && result.getRecipient() == null)
			Assert.isTrue(result.getSender().equals(this.customerService.findCustomerByPrincipal()) && result.getIsSender());
		else
			Assert.isTrue((result.getSender().equals(this.customerService.findCustomerByPrincipal()) && result.getIsSender()) || result.getRecipient().equals(this.customerService.findCustomerByPrincipal()) && !result.getIsSender());

		return result;
	}

	public void save(final Collection<Chirp> chirps) {
		this.chirpRepository.save(chirps);
	}

	public void save(final Chirp chirp) {
		this.chirpRepository.save(chirp);
	}

	public void save(final Collection<Chirp> chirps, final Collection<Attachment> attachments, final Integer eventId) {

		Event event;
		Customer principal;

		principal = this.customerService.findCustomerByPrincipal();

		event = this.eventService.findOne(eventId);
		Assert.isTrue(event.getManager().equals(principal), "chirp.broadcast.principal.error");
		Assert.isTrue(!chirps.isEmpty(), "chirp.broadcast.empty.error");
		for (final Chirp chirp : chirps)
			this.save(chirp, attachments);

	}

	public Chirp save(final Chirp chirp, final Collection<Attachment> attachments) {
		Chirp result, copyChirp, savedCopyChirp;
		Customer sender;

		Assert.notNull(chirp.getRecipient(), "El mensaje debe tener un destinatario");

		Assert.notNull(chirp.getSender(), "El mensaje debe tener un remitente");

		sender = this.customerService.findCustomerByPrincipal();

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
		final int senderId = this.customerService.findCustomerByPrincipal().getId();
		final List<Chirp> result = this.chirpRepository.findSentChirpOfChorbi(senderId);
		return result;
	}

	//Devuelve los mensajes que ha recibido el chorbi	
	public List<Chirp> findReceivedChirpOfPrincipal() {
		final int recipientId = this.customerService.findCustomerByPrincipal().getId();
		this.customerService.findAll();
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

	public List<Chirp> reconstruct(final ChirpBroadcastForm chirpBroadcastForm, final BindingResult binding) {
		List<Chirp> chirps;
		List<Chorbi> recipients;

		chirps = new LinkedList<Chirp>();
		//TODO: hacer consulta paginada. NOTA: Si se hace aqui consulta paginada, no tiene sentido
		// el planteamiento de este metodo, ya que crea un chirp por cada chorbi y al final tendras
		// en memoria tantos chirps como chorbis registrados en el evento. Habría reconstruir solo 
		// uno y en el save guardarlos todos.
		recipients = new LinkedList<Chorbi>(this.registerService.findChorbiesForEvent(chirpBroadcastForm.getEvent()));

		for (final Chorbi chorbi : recipients) {
			Chirp aux;

			aux = this.create(chorbi.getId());
			aux.setText(chirpBroadcastForm.getText());
			aux.setSubject(chirpBroadcastForm.getSubject());

			chirps.add(aux);

		}
		if (!chirps.isEmpty()) {
			this.validator.validate(chirps.get(0), binding);
			if (!binding.hasErrors()) {
				final List<Attachment> attachments = new LinkedList<Attachment>(chirpBroadcastForm.getAttachments());
				for (final Attachment attachment : attachments) {
					attachment.setChirp(chirps.get(0));
					this.validator.validate(attachment, binding);
				}
			}
		}
		return chirps;
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
		final Customer recipient = this.customerService.findOne(chirp.getSender().getId());
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
