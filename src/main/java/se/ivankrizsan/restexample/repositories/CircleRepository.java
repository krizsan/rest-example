package se.ivankrizsan.restexample.repositories;

import se.ivankrizsan.restexample.domain.Circle;
import se.ivankrizsan.restexample.repositories.customisation.JpaRepositoryCustomisations;

/**
 * Spring Data JPA mRepository for circles.
 *
 * @author Ivan Krizsan
 */
public interface CircleRepository extends JpaRepositoryCustomisations<Circle> {
}
