
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Manager extends Customer {

	// Attributes -------------------------------------------------------------

	private String	company;
	private String	VAT;


	@NotBlank
	@SafeHtml
	public String getCompany() {
		return this.company;
	}
	public void setCompany(final String company) {
		this.company = company;
	}

	@NotBlank
	@SafeHtml
	public String getVAT() {
		return this.VAT;
	}
	public void setVAT(final String vAT) {
		this.VAT = vAT;
	}

	// Relationships -------------------------------------------------------------

}
