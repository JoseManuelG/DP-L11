
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "city"), @Index(columnList = "province"), @Index(columnList = "country"), @Index(columnList = "state")
})
public class Coordinates extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	state;
	private String	country;
	private String	province;
	private String	city;


	@NotNull
	public String getState() {
		return this.state;
	}

	public void setState(final String state) {
		this.state = state;
	}

	@NotNull
	public String getCountry() {
		return this.country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	@NotNull
	public String getProvince() {
		return this.province;
	}

	public void setProvince(final String province) {
		this.province = province;
	}

	@NotNull
	public String getCity() {
		return this.city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	// Relationships ----------------------------------------------------------

}
