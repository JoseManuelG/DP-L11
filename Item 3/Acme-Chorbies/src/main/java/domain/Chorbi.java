
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "desiredRelationship"), @Index(columnList = "genre"), @Index(columnList = "description"), @Index(columnList = "birthDate")
})
public class Chorbi extends Actor {

	// Attributes -------------------------------------------------------------

	private String	picture;
	private String	description;
	private String	desiredRelationship;
	private Date	birthDate;
	private String	genre;
	@SuppressWarnings("unused")
	private boolean	banned;
	@SuppressWarnings("unused")
	private int		age;


	@URL
	@NotBlank
	@SafeHtml
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	@NotBlank
	@SafeHtml
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Pattern(regexp = "^activities$|^friendship$|^love$")
	public String getDesiredRelationship() {
		return this.desiredRelationship;
	}

	public void setDesiredRelationship(final String desiredRelationship) {
		this.desiredRelationship = desiredRelationship;
	}

	@Past
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(final Date birthDate) {
		this.birthDate = birthDate;
	}

	@Pattern(regexp = "^man$|^woman$")
	public String getGenre() {
		return this.genre;
	}

	public void setGenre(final String genre) {
		this.genre = genre;
	}
	@Transient
	public boolean getBanned() {
		return !this.getUserAccount().isEnabled();
	}

	@Transient
	public int getAge() {
		LocalDate birthdate, now;
		Period period;

		birthdate = new LocalDate(this.birthDate);
		now = new LocalDate();
		period = new Period(birthdate, now, PeriodType.yearMonthDay());

		return period.getYears();
	}

	public void setAge(final int age) {
		this.age = age;
	}


	// Relationships ----------------------------------------------------------

	private Coordinates	coordinates;


	@Valid
	@NotNull
	@OneToOne(optional = false)
	public Coordinates getCoordinates() {
		return this.coordinates;
	}

	public void setCoordinates(final Coordinates coordinates) {
		this.coordinates = coordinates;
	}

}
