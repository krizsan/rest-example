package se.ivankrizsan.restexample.repositories;

import org.springframework.data.repository.CrudRepository;
import se.ivankrizsan.restexample.domain.Circle;

/**
 * Spring Data JPA mRepository for circles.
 *
 * @author Ivan Krizsan
 */
public interface CircleRepository extends CrudRepository<Circle, Long> {
}
