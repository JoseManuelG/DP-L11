
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Access(AccessType.PROPERTY)
public class Register extends DomainEntity {

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	private Event	event;
	private Chorbi	chorbi;


	@ManyToOne(optional = false)
	public Event getEvent() {
		return this.event;
	}
	public void setEvent(final Event event) {
		this.event = event;
	}

	@ManyToOne(optional = false)
	public Chorbi getChorbi() {
		return this.chorbi;
	}
	public void setChorbi(final Chorbi chorbi) {
		this.chorbi = chorbi;
	}

}
