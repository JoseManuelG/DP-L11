
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private long	cachedTime;


	@Min(0)
	public long getCachedTime() {
		return this.cachedTime;
	}

	public void setCachedTime(final long cachedTime) {
		this.cachedTime = cachedTime;
	}

	// Relationships ----------------------------------------------------------

}
