
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chirp;

@Repository
public interface ChirpRepository extends JpaRepository<Chirp, Integer> {

	//Find all the sended messages for a given chorbi
	@Query("select m from Chirp m where m.sender.id=?1 and m.isSender=true")
	public List<Chirp> findSentChirpOfChorbi(int senderId);

	//Find all the received messages for a given chorbi
	@Query("select m from Chirp m where m.recipient.id=?1 and m.isSender=false")
	public List<Chirp> findReceivedChirpOfChorbi(int recipientId);

	//Find all the sended CopyChirps for a given chorbi
	@Query("select m from Chirp m where m.sender.id=?1 and m.isSender=false")
	public List<Chirp> findSentChirpOfChorbi2(int senderId);

	//Find all the received CopyChirps for a given chorbi
	@Query("select m from Chirp m where m.recipient.id=?1 and m.isSender=true")
	public List<Chirp> findReceivedChirpOfChorbi2(int recipientId);

}
