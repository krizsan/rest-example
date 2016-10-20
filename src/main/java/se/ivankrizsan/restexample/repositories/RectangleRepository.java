package se.ivankrizsan.restexample.repositories;

import se.ivankrizsan.restexample.domain.Rectangle;
import se.ivankrizsan.restexample.repositories.customisation.JpaRepositoryCustomisations;

/**
 * Spring Data JPA mRepository for rectangles.
 *
 * @author Ivan Krizsan
 */
public interface RectangleRepository extends JpaRepositoryCustomisations<Rectangle> {
}
