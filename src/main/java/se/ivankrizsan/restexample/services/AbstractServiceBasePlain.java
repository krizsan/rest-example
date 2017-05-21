package se.ivankrizsan.restexample.services;

import org.springframework.transaction.annotation.Transactional;
import se.ivankrizsan.restexample.domain.LongIdEntity;
import se.ivankrizsan.restexample.repositories.customisation.JpaRepositoryCustomisations;

import java.util.List;
import java.util.Optional;

/**
 * Abstract base class for services that has operations for creating, reading,
 * updating and deleting entities.
 * Synchronous version.
 *
 * @param <E> Entity type.
 * @author Ivan Krizsan
 */
@Transactional
public abstract class AbstractServiceBasePlain<E extends LongIdEntity> {
    /* Constant(s): */

    /* Instance variable(s): */
    protected JpaRepositoryCustomisations<E> mRepository;

    /**
     * Creates a mService instance that will use the supplied repository for
     * entity persistence.
     *
     * @param inRepository Entity repository.
     */
    public AbstractServiceBasePlain(final JpaRepositoryCustomisations<E> inRepository) {
        mRepository = inRepository;
    }

    /**
     * Saves the supplied entity.
     *
     * @param inEntity Entity to save.
     * @return Saved entity.
     */
    public E save(final E inEntity) {
        final E theSavedEntity = mRepository.save(inEntity);
        return theSavedEntity;
    }

    /**
     * Updates the supplied entity.
     *
     * @param inEntity Entity to update.
     * @return Updated entity.
     */
    public E update(final E inEntity) {
        final E theUpdatedEntity = mRepository.persist(inEntity);
        return theUpdatedEntity;
    }

    /**
     * Finds the entity having supplied id.
     *
     * @param inEntityId Id of entity to retrieve.
     * @return Found entity, or null if no entity is found.
     */
    @Transactional(readOnly = true)
    public Optional<E> find(final Long inEntityId) {
        final Optional<E> theEntity = mRepository.findById(inEntityId);
        return theEntity;
    }

    /**
     * Finds all the entities.
     *
     * @return List of entities.
     */
    @Transactional(readOnly = true)
    public List<E> findAll() {
        final List<E> theEntitiesList = mRepository.findAll();
        return theEntitiesList;
    }

    /**
     * Deletes the entity having supplied id.
     *
     * @param inId Id of entity to delete.
     */
    public void delete(final Long inId) {
        mRepository.deleteById(inId);
    }

    /**
     * Deletes all entities.
     */
    public void deleteAll() {
        mRepository.deleteAll();
    }
}
