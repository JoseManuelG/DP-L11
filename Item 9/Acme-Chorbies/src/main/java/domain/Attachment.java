
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Attachment extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	name;
	private String	url;


	@SafeHtml
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@URL
	@SafeHtml
	public String getUrl() {
		return this.url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}


	// Relationships ----------------------------------------------------------

	private Chirp	chirp;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Chirp getChirp() {
		return this.chirp;
	}

	public void setChirp(final Chirp chirp) {
		this.chirp = chirp;
	}

}
