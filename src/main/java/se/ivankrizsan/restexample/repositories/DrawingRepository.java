package se.ivankrizsan.restexample.repositories;

import org.springframework.data.repository.CrudRepository;
import se.ivankrizsan.restexample.domain.Drawing;

/**
 * Spring Data JPA mRepository for drawings.
 *
 * @author Ivan Krizsan
 */
public interface DrawingRepository extends CrudRepository<Drawing, Long> {
}
