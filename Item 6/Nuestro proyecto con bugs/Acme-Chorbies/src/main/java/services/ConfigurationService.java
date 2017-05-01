
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ConfigurationRepository;
import domain.Configuration;
import forms.ConfigurationForm;

@Service
@Transactional
public class ConfigurationService {

	// Managed Repository --------------------------------------
	@Autowired
	private ConfigurationRepository	configurationRepository;

	// Supported Services--------------------------------------
	@Autowired
	private Validator				validator;

	@Autowired
	private AdministratorService	administratorService;


	// Simple CRUD methods-------------------------------------------------------------------

	public Configuration save(final Configuration configuration) {
		Configuration result;

		Assert.notNull(configuration, "configuration.error.null");
		Assert.isTrue(this.administratorService.findAdministratorByPrincipal().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMINISTRATOR"), "configuration.error.notadmin");
		result = this.configurationRepository.save(configuration);
		return result;
	}
	public void flush() {
		this.configurationRepository.flush();
	}

	public Configuration findConfiguration() {

		final Configuration result = this.configurationRepository.findConfiguration();

		return result;
	}

	// Other Business Methods --------------------------------------------------------------------

	public Configuration reconstruct(final ConfigurationForm configurationForm, final BindingResult binding) {
		Configuration result, res;

		result = this.findConfiguration();
		res = new Configuration();

		final long hours = configurationForm.getHours() * 3600000;
		final long minutes = configurationForm.getMinutes() * 60000;
		final long seconds = configurationForm.getSeconds() * 1000;

		final long cacheTime = hours + minutes + seconds;

		res.setCachedTime(cacheTime);
		res.setChorbiFee(configurationForm.getChorbiFee());
		res.setManagerFee(configurationForm.getManagerFee());
		res.setId(result.getId());
		res.setVersion(result.getVersion());

		this.validator.validate(res, binding);

		return res;
	}

}
