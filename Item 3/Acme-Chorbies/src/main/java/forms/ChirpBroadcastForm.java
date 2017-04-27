
package forms;

import java.util.Collection;
import java.util.LinkedList;

import domain.Attachment;

public class ChirpBroadcastForm {

	private String					subject;
	private String					text;
	private Integer					event;
	private LinkedList<Attachment>	attachments;


	public String getText() {
		return this.text;
	}
	public void setText(final String text) {
		this.text = text;
	}
	public Integer getEvent() {
		return this.event;
	}
	public void setEvent(final Integer event) {
		this.event = event;
	}
	public Collection<Attachment> getAttachments() {
		return this.attachments;
	}
	public void setAttachments(final LinkedList<Attachment> attachments) {
		this.attachments = attachments;
	}

	public ChirpBroadcastForm() {
		this.attachments = new LinkedList<Attachment>();
	}

	//Metodo para a�adir un nuevo espacio que rellenar el formulario
	public void addAttachmentSpace() {
		//a�adia un null y le he puesto que a�ada una ttachment vacio porque a�adir 
		//null no tiene mucho sentido
		this.attachments.add(new Attachment());
	}
	//Metodo para eliminar el ultimo attachment del formulario
	public void removeAttachmentSpace() {
		this.attachments.removeLast();
	}
	public String getSubject() {
		return this.subject;
	}
	public void setSubject(final String subject) {
		this.subject = subject;
	}

}
