
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Banner;

public interface BannerRepository extends JpaRepository<Banner, Integer> {

}
