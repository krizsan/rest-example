package se.ivankrizsan.restexample.repositories.customisation;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;
import se.ivankrizsan.restexample.domain.LongIdEntity;

import javax.persistence.EntityManager;

/**
 * This class implements the Spring Data JPA repository customisations.
 * Need to annotate the persist method in this class with the Spring
 * annotation @Transactional since the superclass {code SimpleJpaRepository}
 * is annotated with @Transactional that sets transactions to read-only
 * for all methods.
 *
 * @param <T> Entity type.
 * @author Ivan Krizsan
 */
@NoRepositoryBean
public class JpaRepositoryCustomisationsImpl<T> extends SimpleJpaRepository<T, Long> implements
    JpaRepositoryCustomisations<T> {
    /* Constant(s): */

    /* Instance variable(s): */
    protected EntityManager mEntityManager;

    /**
     * Creates a repository instance for the entity specified by the supplied entity
     * information that uses the supplied entity manager.
     * Constructor from superclass.
     *
     * @param inEntityInformation Entity information.
     * @param inEntityManager Entity manager.
     */
    public JpaRepositoryCustomisationsImpl(final JpaEntityInformation<T, ?> inEntityInformation,
        final EntityManager inEntityManager) {
        super(inEntityInformation, inEntityManager);
        mEntityManager = inEntityManager;
    }

    /**
     * Creates a repository instance for the supplied entity type that uses the
     * supplied entity manager.
     * Constructor from superclass.
     *
     * @param inEntityType Entity type.
     * @param inEntityManager Entity manager.
     */
    public JpaRepositoryCustomisationsImpl(final Class<T> inEntityType,
        final EntityManager inEntityManager) {
        super(inEntityType, inEntityManager);
        mEntityManager = inEntityManager;
    }

    @Transactional
    @Override
    public T persist(final T inEntity) {
        T theSavedEntity = inEntity;
        final Long theEntityId = ((LongIdEntity) theSavedEntity).getId();
        if ((theEntityId != null) && existsById(theEntityId)) {
            theSavedEntity = mEntityManager.merge(inEntity);
        } else {
            mEntityManager.persist(inEntity);
        }
        mEntityManager.flush();

        return theSavedEntity;
    }
}
