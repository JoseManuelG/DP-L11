
package services;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ChorbiRepository;
import repositories.DashboardRepository;
import repositories.LikesRepository;
import repositories.ManagerRepository;
import domain.Chorbi;
import domain.Manager;

@Service
@Transactional
public class DashboardService {

	// Managed Repository --------------------------------------
	@Autowired
	private DashboardRepository	dashboardRepository;

	@Autowired
	private ChorbiRepository	chorbiRepository;

	@Autowired
	private LikesRepository		likesRepository;

	@Autowired
	private ManagerRepository	managerRepository;


	//Dashboard - 01
	public List<Object[]> numberOfChorbiesPerCountry() {
		return this.dashboardRepository.numberOfChorbiesPerCountry();
	}

	//Dashboard - 01
	public List<Object[]> numberOfChorbiesPerCity() {
		return this.dashboardRepository.numberOfChorbiesPerCity();
	}

	//Dashboard - 02
	public Integer minAgeOfChorbies() {
		return this.dashboardRepository.minAgeOfChorbies();
	}

	//Dashboard - 02
	public Integer maxAgeOfChorbies() {
		return this.dashboardRepository.maxAgeOfChorbies();
	}

	//Dashboard - 02
	public Double averageAgeOfChorbies() {
		Double res;
		res = this.dashboardRepository.averageAgeOfChorbies();
		if (res == null)
			res = 0.;
		return res;
	}

	//Dashboard - 03
	public Double ratioChorbiesWithoutValidCreditCard() {
		Long chorbies;
		Double res;
		LocalDate now, lastDayOfMonth;
		Boolean isLastDay;

		chorbies = this.chorbiRepository.count();

		now = new LocalDate();
		lastDayOfMonth = now.dayOfMonth().withMaximumValue();
		isLastDay = now.equals(lastDayOfMonth);

		if (chorbies > 0)
			res = this.dashboardRepository.countValidCreditCard(isLastDay) / chorbies;
		else
			res = 0.;

		return res;
	}

	//Dashboard - 04
	public Double ratioChorbiesWhoDesireActivities() {
		Long chorbies;
		Double res;

		chorbies = this.chorbiRepository.count();

		if (chorbies > 0)
			res = this.dashboardRepository.countChorbiesWhoDesireActivities() / chorbies;
		else
			res = 0.;

		return res;
	}

	//Dashboard - 04
	public Double ratioChorbiesWhoDesireFriendship() {
		Long chorbies;
		Double res;

		chorbies = this.chorbiRepository.count();

		if (chorbies > 0)
			res = this.dashboardRepository.countChorbiesWhoDesireFriendship() / chorbies;
		else
			res = 0.;

		return res;
	}

	//Dashboard - 04
	public Double ratioChorbiesWhoDesireLove() {
		Long chorbies;
		Double res;

		chorbies = this.chorbiRepository.count();

		if (chorbies > 0)
			res = this.dashboardRepository.countChorbiesWhoDesireLove() / chorbies;
		else
			res = 0.;

		return res;
	}

	//Dashboard - 05
	public List<Chorbi> chorbiesOrderedByLikes() {
		final List<Chorbi> res;

		res = new ArrayList<Chorbi>();
		res.addAll(this.dashboardRepository.findChorbiesOrderedByLikes());
		res.addAll(this.dashboardRepository.findChorbiesWithoutLikes());

		return res;
	}

	//Dashboard - 06
	public Integer minNumberOfLikesPerChorbi() {
		Long chorbies;
		List<Long> counts;
		Integer res;

		counts = new ArrayList<Long>();

		chorbies = this.chorbiRepository.count();
		counts.addAll(this.dashboardRepository.minNumberOfLikesPerChorbi());

		if (chorbies > 0 && counts.size() == chorbies)
			res = counts.get(0).intValue();
		else
			res = 0;

		return res;
	}

	//Dashboard - 06
	public Integer maxNumberOfLikesPerChorbi() {
		List<Long> counts;
		Integer res;

		counts = new ArrayList<Long>();
		counts.addAll(this.dashboardRepository.maxNumberOfLikesPerChorbi());

		if (counts.size() > 0)
			res = counts.get(0).intValue();
		else
			res = 0;

		return res;
	}
	//Dashboard - 06
	public Double avgNumberOfLikesPerChorbi() {
		Long chorbies, likes;
		Double res;

		chorbies = this.chorbiRepository.count();
		likes = this.likesRepository.count();

		if (chorbies > 0)
			res = 1.0 * likes / chorbies;
		else
			res = 0.;

		return res;
	}

	//Dashboard - 07
	public Double averageOfChirpsReceivedPerChorbi() {
		Long chorbies;
		Double chirps;
		Double res;

		chorbies = this.chorbiRepository.count();
		chirps = this.dashboardRepository.averageOfChirpsReceivedPerChorbi();

		if (chorbies > 0)
			res = chirps / chorbies;
		else
			res = 0.;

		return res;
	}

	//Dashboard - 07
	public Integer minOfChirpsReceivedPerChorbi() {
		Long chorbies;
		List<Long> chirps;
		Integer res;

		chorbies = this.chorbiRepository.count();
		chirps = new ArrayList<Long>();
		chirps = this.dashboardRepository.minOfChirpsReceivedPerChorbi();

		if (chorbies > 0 && chirps.size() == chorbies)
			res = chirps.get(0).intValue();
		else
			res = 0;

		return res;
	}

	//Dashboard - 07
	public Integer maxOfChirpsReceivedPerChorbi() {
		List<Long> chirps;
		Integer res;

		chirps = new ArrayList<Long>();
		chirps = this.dashboardRepository.maxOfChirpsReceivedPerChorbi();

		if (chirps.size() > 0)
			res = chirps.get(0).intValue();
		else
			res = 0;

		return res;
	}

	//Dashboard - 08
	public Double averageOfChirpsSentPerChorbi() {
		Long chorbies;
		Double chirps;
		Double res;

		chorbies = this.chorbiRepository.count();
		chirps = this.dashboardRepository.averageOfChirpsSentPerChorbi();

		if (chorbies > 0)
			res = chirps / chorbies;
		else
			res = 0.;

		return res;
	}

	//Dashboard - 08
	public Integer minOfChirpsSentPerChorbi() {
		Long chorbies;
		List<Long> chirps;
		Integer res;

		chorbies = this.chorbiRepository.count();
		chirps = new ArrayList<Long>();
		chirps = this.dashboardRepository.minOfChirpsSentPerChorbi();

		if (chorbies > 0 && chirps.size() == chorbies)
			res = chirps.get(0).intValue();
		else
			res = 0;

		return res;
	}

	//Dashboard - 08
	public Integer maxOfChirpsSentPerChorbi() {
		List<Long> chirps;
		Integer res;

		chirps = new ArrayList<Long>();
		chirps = this.dashboardRepository.maxOfChirpsSentPerChorbi();

		if (chirps.size() > 0)
			res = chirps.get(0).intValue();
		else
			res = 0;

		return res;
	}

	//Dashboard - 09
	public List<Chorbi> findChorbiesWhoGotMoreChirps() {
		List<Chorbi> res;
		Long max;

		max = this.maxOfChirpsReceivedPerChorbi() + 0l;
		res = new ArrayList<Chorbi>();
		res.addAll(this.dashboardRepository.findChorbiesWhoGotMoreChirps(max));

		if (res.size() == 0)
			res.addAll(this.chorbiRepository.findAll());

		return res;
	}

	//Dashboard - 10
	public List<Chorbi> findChorbiesWhoSentMoreChirps() {
		List<Chorbi> res;
		Long max;

		max = this.maxOfChirpsSentPerChorbi() + 0l;
		res = new ArrayList<Chorbi>();
		res.addAll(this.dashboardRepository.findChorbiesWhoSentMoreChirps(max));

		if (res.size() == 0)
			res.addAll(this.chorbiRepository.findAll());

		return res;
	}

	//Dashboard - 11
	public List<Object[]> getManagersOrderedByEvents() {

		List<Object[]> res;
		List<Manager> managers;
		Object[] aux;

		res = new ArrayList<Object[]>();
		managers = new ArrayList<Manager>();

		res.addAll(this.dashboardRepository.getManagersOrderedByEvents());

		managers.addAll(this.dashboardRepository.getManagersWithNoEvents());

		for (final Manager m : managers) {
			aux = new Object[2];
			aux[0] = m;
			aux[1] = 0;
			res.add(aux);
		}

		return res;
	}

	//Dashboard - 12
	public List<Manager> getManagersWithChargedFee() {

		List<Manager> res;

		res = new ArrayList<Manager>();

		res.addAll(this.managerRepository.findAll());

		return res;
	}

	//Dashboard - 13
	public List<Object[]> getChorbiesOrderedByEvents() {

		List<Object[]> res;
		List<Chorbi> chorbies;
		Object[] aux;

		res = new ArrayList<Object[]>();
		chorbies = new ArrayList<Chorbi>();

		res.addAll(this.dashboardRepository.getChorbiesOrderedByRegisters());

		chorbies.addAll(this.dashboardRepository.getChorbiesWithNoRegisters());

		for (final Chorbi c : chorbies) {
			aux = new Object[2];
			aux[0] = c;
			aux[1] = 0;
			res.add(aux);
		}
		return res;
	}

	//Dashboard - 14
	public List<Chorbi> getChorbiesWithChargedFee() {

		List<Chorbi> res;

		res = new ArrayList<Chorbi>();

		res.addAll(this.chorbiRepository.findAll());

		return res;
	}

	//Dashboard - 15
	public List<Object[]> getChorbiesWithMinMaxAvgStars() {
		List<Object[]> res;
		List<Chorbi> chorbiesNoLikes;
		Object[] aux;

		res = new ArrayList<Object[]>();
		chorbiesNoLikes = new ArrayList<Chorbi>();

		res.addAll(this.dashboardRepository.getChorbiesWithMinMaxAvgStars());
		chorbiesNoLikes.addAll(this.dashboardRepository.getChorbiesWithNoLikes());

		for (final Chorbi c : chorbiesNoLikes) {
			aux = new Object[4];
			aux[0] = c;
			aux[1] = 0;
			aux[2] = 0;
			aux[3] = 0.;
			res.add(aux);
		}

		return res;
	}

	//Dashboard - 16
	public List<Object[]> getChorbiesWithAvgStarsOrderedByAvgStars() {
		List<Object[]> res;
		List<Chorbi> chorbiesNoLikes;
		Object[] aux;

		res = new ArrayList<Object[]>();
		chorbiesNoLikes = new ArrayList<Chorbi>();

		res.addAll(this.dashboardRepository.getChorbiesWithAvgStarsOrderedByAvgStars());
		chorbiesNoLikes.addAll(this.dashboardRepository.getChorbiesWithNoLikes());

		for (final Chorbi c : chorbiesNoLikes) {
			aux = new Object[2];
			aux[0] = c;
			aux[1] = 0.;
			res.add(aux);
		}

		return res;
	}

}
