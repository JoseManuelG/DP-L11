
package forms;

import domain.Manager;

public class ManagerForm extends ActorForm {

	private String	company;
	private String	VAT;


	//Constructor
	public ManagerForm() {
		super();
	}

	public ManagerForm(final Manager manager) {
		super(manager);

		this.setCompany(manager.getCompany());
		this.setVAT(manager.getVAT());

	}

	//attributes------------
	public String getCompany() {
		return this.company;
	}

	public void setCompany(final String company) {
		this.company = company;
	}

	public String getVAT() {
		return this.VAT;
	}

	public void setVAT(final String vAT) {
		this.VAT = vAT;
	}

}
