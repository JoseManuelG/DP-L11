
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.LikesRepository;
import domain.Actor;
import domain.Chorbi;
import domain.Likes;

@Service
@Transactional
public class LikesService {

	//Managed Repository-----------------------------

	@Autowired
	private LikesRepository	likesRepository;

	//Supporting services-----------------------------

	@Autowired
	private ChorbiService	chorbiService;

	@Autowired
	private ActorService	actorService;
	@Autowired
	private Validator		validator;


	//Constructors------------------------------------

	public LikesService() {
		super();
	}

	//Simple CRUD methods----------------------------

	public Likes create(final Chorbi chorbi) {
		Likes result;
		Chorbi principal;

		principal = this.chorbiService.findChorbiByPrincipal();

		result = new Likes();
		result.setLiked(chorbi);
		result.setLiker(principal);

		return result;
	}

	public Likes findOne(final int likeId) {
		Likes result;

		result = this.likesRepository.findOne(likeId);
		Assert.notNull(result);

		return result;
	}

	public Likes save(final Likes likes) {
		Date currentMoment;
		Likes result;

		Assert.notNull(likes, "likes.null.error");
		Assert.isTrue(likes.getId() == 0, "likes.edit.error");
		Assert.isTrue(likes.getLiker().equals(this.chorbiService.findChorbiByPrincipal()), "likes.principal.error");
		Assert.isTrue(!likes.getLiked().equals(likes.getLiker()), "likes.self.error");
		Assert.isTrue(this.findUniqueLike(likes.getLiked().getId()), "likes.unique.error");

		currentMoment = new Date(System.currentTimeMillis() - 10000);
		likes.setMoment(currentMoment);

		result = this.likesRepository.save(likes);

		Assert.notNull(result, "likes.commit.error");

		return result;
	}

	public void delete(final Likes likes) {
		Assert.notNull(likes, "El attachment no puede ser nulo");
		Assert.isTrue(likes.getId() != 0, "El attachment debe estar antes en la base de datos");

		this.likesRepository.exists(likes.getId());
		Assert.isTrue(this.chorbiService.findChorbiByPrincipal().equals(likes.getLiker()));

		this.likesRepository.delete(likes);

	}

	//Other bussiness methods------------------------

	public Likes reconstruct(final Likes likes, final BindingResult binding) {
		Likes result;
		Chorbi chorbi;
		result = new Likes();

		chorbi = this.chorbiService.findChorbiByPrincipal();
		// Setear lo que viene del formulario:
		result.setComment(likes.getComment());
		result.setLiked(likes.getLiked());
		result.setStars(likes.getStars());

		// Setear lo que no viene del formulario:
		result.setLiker(chorbi);
		result.setMoment(new Date(System.currentTimeMillis() - 100));
		result.setId(likes.getId());
		result.setVersion(likes.getVersion());
		this.validator.validate(result, binding);
		return result;
	}

	public Collection<Likes> findReceivedLikesOfChorbi(final int chorbiId) {

		final Collection<Likes> result = this.likesRepository.findReceivedLikesOfChorbi(chorbiId);
		return result;
	}

	public Collection<Likes> findSentLikesOfPrincipal() {
		final int senderId = this.chorbiService.findChorbiByPrincipal().getId();
		final Collection<Likes> result = this.findSentLikesOfChorbi(senderId);
		return result;
	}

	public Collection<Likes> findSentLikesOfChorbi(final int chorbiId) {
		final Collection<Likes> result = this.likesRepository.findSentLikesOfChorbi(chorbiId);
		return result;
	}

	public Boolean findUniqueLike(final int chorbiId) {
		Boolean res;
		final Likes likes;
		Actor principal;

		principal = this.actorService.findActorByPrincipal();
		likes = this.likesRepository.findUniqueLike(principal.getId(), chorbiId);
		res = likes == null;

		return res;
	}
	public void deleteFromChorbi(final Chorbi chorbi) {
		Collection<Likes> likes;

		likes = new ArrayList<Likes>();
		likes.addAll(this.findReceivedLikesOfChorbi(chorbi.getId()));
		likes.addAll(this.findSentLikesOfChorbi(chorbi.getId()));

		this.likesRepository.delete(likes);

	}
}
