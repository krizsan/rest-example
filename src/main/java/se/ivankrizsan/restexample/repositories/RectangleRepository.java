package se.ivankrizsan.restexample.repositories;

import org.springframework.data.repository.CrudRepository;
import se.ivankrizsan.restexample.domain.Rectangle;

/**
 * Spring Data JPA mRepository for rectangles.
 *
 * @author Ivan Krizsan
 */
public interface RectangleRepository extends CrudRepository<Rectangle, Long> {
}
