
package forms;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Chorbi;

public class ChorbiForm extends ActorForm {

	private String	picture;
	private String	description;
	private String	desiredRelationship;
	private String	genre;
	private Date	birthDate;
	private String	state;
	private String	country;
	private String	province;
	private String	city;


	//Constructor
	public ChorbiForm() {
		super();
	}

	public ChorbiForm(final Chorbi chorbi) {
		super(chorbi);

		this.setBirthDate(chorbi.getBirthDate());
		this.setPicture(chorbi.getPicture());
		this.setDescription(chorbi.getDescription());
		this.setDesiredRelationship(chorbi.getDesiredRelationship());
		this.setGenre(chorbi.getGenre());
		this.setCountry(chorbi.getCoordinates().getCountry());
		this.setCity(chorbi.getCoordinates().getCity());
		this.setState(chorbi.getCoordinates().getState());
		this.setProvince(chorbi.getCoordinates().getProvince());

	}

	//attributes------------

	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getDesiredRelationship() {
		return this.desiredRelationship;
	}

	public void setDesiredRelationship(final String desiredRelationship) {
		this.desiredRelationship = desiredRelationship;
	}

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(final Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getGenre() {
		return this.genre;
	}

	public void setGenre(final String genre) {
		this.genre = genre;
	}

	public String getState() {
		return this.state;
	}

	public void setState(final String state) {
		this.state = state;
	}

	@NotBlank
	public String getCountry() {
		return this.country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(final String province) {
		this.province = province;
	}

	@NotBlank
	public String getCity() {
		return this.city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

}
