
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AttachmentRepository;
import domain.Attachment;
import domain.Chirp;
import domain.Chorbi;

@Service
@Transactional
public class AttachmentService {

	//Managed Repository--------------------------------------------------------------------
	@Autowired
	private AttachmentRepository	attachmentRepository;


	//Supported Services--------------------------------------------------------------------
	//Simple CRUD methods------------------------------------------------------------
	public Attachment create(final Chirp chirp) {
		final Attachment result = new Attachment();
		result.setChirp(chirp);
		return result;
	}

	public Attachment save(final Attachment attachment) {
		Attachment result;
		result = this.attachmentRepository.save(attachment);
		return result;
	}

	public void delete(final Attachment attachment) {
		Assert.notNull(attachment, "El objeto no puede ser nulo");
		Assert.isTrue(attachment.getId() != 0, "El objeto no puede tener id 0");
		this.attachmentRepository.delete(attachment);

	}

	//Other Bussnisnes methods------------------------------------------------------------
	//Devuelve la lista de Attachments de un mensaje dado
	public List<Attachment> findAttachmentsOfChirp(final Chirp chirp) {
		final List<Attachment> result = this.attachmentRepository.findAttachmentsOfChirp(chirp.getId());
		return result;
	}
	//Guarda los attachments setteandolos al mensaje dado, AVISO: el mensaje debe estar almacenado ya en base de datos si no la ID seria 0.
	public void addAttachments(final Collection<Attachment> attachments, final Chirp chirp) {
		final Attachment attachment = this.create(chirp);
		for (final Attachment a : attachments) {
			attachment.setName(a.getName());
			attachment.setUrl(a.getUrl());
			this.save(attachment);
		}

	}

	//Devuelve una colleción con copias de los attachments de un mensaje, se podria coger los attachments por query en vez de pedirlos como entrada.
	public Collection<Attachment> copyAttachments(final Chirp chirp) {
		final Attachment attachment = this.create(chirp);
		final LinkedList<Attachment> result = new LinkedList<Attachment>();
		for (final Attachment a : this.attachmentRepository.findAttachmentsOfChirp(chirp.getId())) {
			attachment.setName(a.getName());
			attachment.setUrl(a.getUrl());
			result.add(attachment);
		}
		return result;

	}
	//Borra los attachments de un mensaje, se debe llamar antes de borrar el mensaje para evitar problemas de persistencia
	public void deleteAttachmentsOfChirp(final Chirp chirp) {
		final List<Attachment> aux = this.findAttachmentsOfChirp(chirp);
		for (final Attachment attachment : aux)
			this.delete(attachment);

	}

	public void deleteChorbi(final Chorbi chorbi) {
		final Collection<Attachment> attachments = new ArrayList<Attachment>();
		attachments.addAll(this.attachmentRepository.findAttachmentsOfChorbiDeleting(chorbi.getId()));
		this.attachmentRepository.delete(attachments);

	}

}
