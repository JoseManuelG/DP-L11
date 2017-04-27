
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {

	private double	chargedFee;


	@Min(0)
	public double getChargedFee() {
		return this.chargedFee;
	}

	public void setChargedFee(final double chargedFee) {
		this.chargedFee = chargedFee;
	}

}
