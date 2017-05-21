package se.ivankrizsan.restexample.repositories.customisation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.LockModeType;

/**
 * Interface defining custom method(s) added to all the Spring Data JPA repositories
 * in the application.
 *
 * @param <T> Entity type.
 * @author Ivan Krizsan
 */
@NoRepositoryBean
public interface JpaRepositoryCustomisations<T> extends JpaRepository<T, Long> {
    /**
     * Persists the supplied entity.
     * If the entity has an id and previously has been persisted, it will be merged
     *  to the persistence context otherwise it will be inserted into the
     *  persistence context.
     *
     * @param inEntity Entity to persist.
     * @return Persisted entity.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    T persist(T inEntity);
}
