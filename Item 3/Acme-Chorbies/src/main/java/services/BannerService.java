
package services;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.BannerRepository;
import domain.Administrator;
import domain.Banner;

@Service
@Transactional
public class BannerService {

	// Managed Repository --------------------------------------

	@Autowired
	private BannerRepository		bannerRepository;

	// Supporting Services --------------------------------------

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private Validator				validator;


	// Simple CRUD methods-------------------------------------------------------------------

	public Banner create() {
		final Banner banner = new Banner();
		return banner;
	}

	public Banner findOne(final int bannerId) {
		Banner result;
		Assert.isTrue(bannerId > 0, "banner.error.id.invalid");

		result = this.bannerRepository.findOne(bannerId);
		return result;
	}

	public Banner save(final Banner banner) {
		Banner result;
		Assert.notNull(banner, "banner.error.null");
		Assert.isTrue(this.administratorService.findAdministratorByPrincipal().getClass().equals(Administrator.class), "banner.error.notadmin");
		result = this.bannerRepository.save(banner);
		return result;
	}

	public Collection<Banner> findAll() {
		return this.bannerRepository.findAll();
	}

	public void delete(final Banner banner) {
		Assert.notNull(banner, "banner.null.error");
		Assert.isTrue(this.bannerRepository.exists(banner.getId()), "banner.exists.error");
		Assert.isTrue(this.administratorService.findAdministratorByPrincipal().getClass().equals(Administrator.class), "banner.error.notadmin");

		this.bannerRepository.delete(banner);

	}

	public void flush() {
		this.bannerRepository.flush();
	}

	// Other business methods-------------------------------------------------------------
	public Banner reconstruct(final Banner banner, final BindingResult binding) {
		final Banner result = this.create();
		if (banner.getId() != 0) {
			final Banner savedBanner = this.findOne(banner.getId());
			result.setId(savedBanner.getId());
			result.setVersion(savedBanner.getVersion());
		}

		result.setImage(banner.getImage());
		result.setLink(banner.getLink());
		this.validator.validate(result, binding);

		return result;
	}

	public Banner randomBanner() {
		Banner result = this.create();
		final List<Banner> banners = this.bannerRepository.findAll();

		if (!banners.isEmpty()) {
			final Random randomIndex = new Random();
			final Integer index = randomIndex.nextInt(banners.size());
			result = banners.get(index);
		}
		return result;
	}
}
