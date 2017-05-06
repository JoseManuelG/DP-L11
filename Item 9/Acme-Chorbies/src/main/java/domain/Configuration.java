
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private long	cachedTime;
	private double	chorbiFee;
	private double	managerFee;


	@Min(0)
	public long getCachedTime() {
		return this.cachedTime;
	}

	public void setCachedTime(final long cachedTime) {
		this.cachedTime = cachedTime;
	}

	@Min(0)
	@Digits(integer = 15, fraction = 2)
	public double getChorbiFee() {
		return this.chorbiFee;
	}

	public void setChorbiFee(final double chorbiFee) {
		this.chorbiFee = chorbiFee;
	}

	@Min(0)
	@Digits(integer = 15, fraction = 2)
	public double getManagerFee() {
		return this.managerFee;
	}

	public void setManagerFee(final double managerFee) {
		this.managerFee = managerFee;
	}

	// Relationships ----------------------------------------------------------

}
