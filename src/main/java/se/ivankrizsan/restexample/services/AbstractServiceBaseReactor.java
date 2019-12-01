package se.ivankrizsan.restexample.services;

import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.ivankrizsan.restexample.domain.LongIdEntity;
import se.ivankrizsan.restexample.repositories.customisation.JpaRepositoryCustomisations;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

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
        return Mono.create(theMonoSink -> {
            try {
                final E theSavedEntity = mRepository.save(inEntity);
                theMonoSink.success(theSavedEntity);
            } catch (final Throwable theException) {
                theMonoSink.error(theException);
            }
        });
    }

    /**
     * Updates the supplied entity.
     *
     * @param inEntity Entity to update.
     * @return Mono that will receive the updated entity, or exception if error occurs.
     */
    public Mono<E> update(final E inEntity) {
        return Mono.create(theMonoSink -> {
            try {
                final E theSavedEntity = mRepository.persist(inEntity);
                theMonoSink.success(theSavedEntity);
            } catch (final Throwable theException) {
                theMonoSink.error(theException);
            }
        });
    }

    /**
     * Finds the entity having supplied id.
     *
     * @param inEntityId Id of entity to retrieve.
     * @return Mono that will receive the found entity or error if error occurs or no
     * entity with supplied id is found.
     */
    @Transactional(readOnly = true)
    public Mono<E> find(final Long inEntityId) {
        return Mono.create(theMonoSink -> {
            try {
                final Optional<E> theFoundEntity = mRepository.findById(inEntityId);
                if (theFoundEntity.isPresent()) {
                    theMonoSink.success(theFoundEntity.get());
                } else {
                    theMonoSink.error(
                        new EntityNotFoundException("Entity with id " + inEntityId + " not found"));
                }
            } catch (final Throwable theException) {
                theMonoSink.error(theException);
            }
        });
    }

    /**
     * Finds all the entities.
     *
     * @return Flux that will receive the found entities or error.
     */
    @Transactional(readOnly = true)
    public Flux<E> findAll() {
        return Flux.create(theFluxSink -> {
            try {
                final List<E> theAllEntities = mRepository.findAll();
                for (final E theEntity : theAllEntities) {
                    theFluxSink.next(theEntity);
                }
                theFluxSink.complete();
            } catch (final Throwable theException) {
                theFluxSink.error(theException);
            }
        });
    }

    /**
     * Deletes the entity having supplied id.
     *
     * @param inEntityId Id of entity to delete.
     * @return Mono that will receive completion or error.
     */
    public Mono<Void> delete(final Long inEntityId) {
        return Mono.create(theMonoSink -> {
            try {
                mRepository.deleteById(inEntityId);
                theMonoSink.success();
            } catch (final Throwable theException) {
                theMonoSink.error(theException);
            }
        });
    }

    /**
     * Deletes all entities.
     *
     * @return Mono that will receive completion or error.
     */
    public Mono<Void> deleteAll() {
        return Mono.create(theMonoSink -> {
            try {
                mRepository.deleteAll();
                theMonoSink.success();
            } catch (final Throwable theException) {
                theMonoSink.error(theException);
            }
        });
    }
}
