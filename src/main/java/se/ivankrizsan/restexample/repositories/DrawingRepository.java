package se.ivankrizsan.restexample.repositories;

import se.ivankrizsan.restexample.domain.Drawing;
import se.ivankrizsan.restexample.repositories.customisation.JpaRepositoryCustomisations;

/**
 * Spring Data JPA mRepository for drawings.
 *
 * @author Ivan Krizsan
 */
public interface DrawingRepository extends JpaRepositoryCustomisations<Drawing> {
}
