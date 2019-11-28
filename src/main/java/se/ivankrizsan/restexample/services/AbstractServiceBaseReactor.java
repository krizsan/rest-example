package se.ivankrizsan.restexample.services;

import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.ivankrizsan.restexample.domain.LongIdEntity;
import se.ivankrizsan.restexample.repositories.customisation.JpaRepositoryCustomisations;

/**
 * Abstract base class for services that has operations for creating, reading,
 * updating and deleting entities.
 * This implementation uses Reactor.
 *
 * @param <E> Entity type.
 * @author Ivan Krizsan
 */
@Transactional
public abstract class AbstractServiceBaseReactor<E extends LongIdEntity> {
    /* Constant(s): */

    /* Instance variable(s): */
    protected JpaRepositoryCustomisations<E> mRepository;

    /**
     * Creates a service instance that will use the supplied repository for entity persistence.
     *
     * @param inRepository Entity repository.
     */
    public AbstractServiceBaseReactor(final JpaRepositoryCustomisations<E> inRepository) {
        mRepository = inRepository;
    }

    /**
     * Saves the supplied entity.
     *
     * @param inEntity Entity to save.
     * @return Mono that will receive the saved entity, or exception if error occurs.
     */
    public Mono<E> save(final E inEntity) {
        return Mono.just(mRepository.save(inEntity));
    }

    /**
     * Updates the supplied entity.
     *
     * @param inEntity Entity to update.
     * @return Mono that will receive the updated entity, or exception if error occurs.
     */
    public Mono<E> update(final E inEntity) {
        return Mono.just(mRepository.persist(inEntity));
    }

    /**
     * Finds the entity having supplied id.
     *
     * @param inEntityId Id of entity to retrieve.
     * @return Mono that will receive the found entity, or exception if error occurs or no entity is found.
     */
    @Transactional(readOnly = true)
    public Mono<E> find(final Long inEntityId) {
        return Mono.justOrEmpty(mRepository.findById(inEntityId));
    }

    /**
     * Finds all the entities.
     *
     * @return Flux that will receive the found entities, or exception if error occurs.
     */
    @Transactional(readOnly = true)
    public Flux<E> findAll() {
        return Flux.fromIterable(mRepository.findAll());
    }

    /**
     * Deletes the entity having supplied id.
     *
     * @param inId Id of entity to delete.
     * @return Mono that will receive completion, or exception if error occurs.
     */
    public Mono<Void> delete(final Long inId) {
        return Mono.fromRunnable(() -> mRepository.deleteById(inId));
    }

    /**
     * Deletes all entities.
     *
     * @return Observable that will receive completion, or exception if error occurs.
     */
    public Mono<Void> deleteAll() {
        return Mono.fromRunnable(() -> mRepository.deleteAll());
    }
}
