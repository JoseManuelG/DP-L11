
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select a from Actor a where a.userAccount.id = ?1")
	public Customer findByUserAccountId(int id);

}
