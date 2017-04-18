
package forms;

import java.util.Date;

import javax.validation.constraints.AssertTrue;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import security.UserAccount;

public class ActorForm {

	private String		confirmPassword;
	private String		name;
	private String		surname;
	private String		email;
	private String		phone;
	private String		picture;
	private String		description;
	private String		desiredRelationship;
	private String		genre;
	private Date		birthDate;
	private Boolean		acepted;
	private UserAccount	userAccount;
	private String		state;
	private String		country;
	private String		province;
	private String		city;


	//Constructor
	public ActorForm() {
		super();
	}
	//attributes------------

	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}
	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return this.email;
	}
	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}
	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@AssertTrue
	public Boolean getAcepted() {
		return this.acepted;
	}

	public void setAcepted(final Boolean acepted) {
		this.acepted = acepted;
	}

	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

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

	public String getCountry() {
		return this.country;
	}

	@NotBlank
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

	@AssertTrue
	public boolean getValid() {
		boolean result;
		if (this.confirmPassword != null)
			result = this.confirmPassword.equals(this.userAccount.getPassword());
		else
			result = this.userAccount.getPassword() == null;

		return result;
	}

}
