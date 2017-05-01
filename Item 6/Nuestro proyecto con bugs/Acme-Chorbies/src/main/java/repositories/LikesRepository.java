
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Likes;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Integer> {

	//Find all the likes done from a given chorbi
	@Query("select m from Likes m where m.liker.id=?1")
	public List<Likes> findSentLikesOfChorbi(int senderId);

	//Find all the likes done for a given chorbi
	@Query("select m from Likes m where m.liked.id=?1")
	public List<Likes> findReceivedLikesOfChorbi(int recipientId);

	//Find all the likes done for a given chorbi and from another chorbi
	@Query("select m from Likes m where m.liker.id=?1 and m.liked.id=?2")
	public Likes findUniqueLike(int likerId, int likedId);

}
