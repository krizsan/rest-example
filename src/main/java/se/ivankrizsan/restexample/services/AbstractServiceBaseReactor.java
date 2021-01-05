package se.ivankrizsan.restexample.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;
import reactor.core.scheduler.Schedulers;
import se.ivankrizsan.restexample.domain.LongIdEntity;
import se.ivankrizsan.restexample.repositories.customisation.JpaRepositoryCustomisations;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractServiceBaseReactor.class);

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
        return Mono.create((Consumer<MonoSink<E>>) theMonoSink -> {
            try {
                LOGGER.info("Saving entity: {}", inEntity);

                final E theSavedEntity = mRepository.save(inEntity);
                theMonoSink.success(theSavedEntity);
            } catch (final Throwable theException) {
                theMonoSink.error(theException);
            }
        })
            .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Updates the supplied entity.
     *
     * @param inEntity Entity to update.
     * @return Mono that will receive the updated entity, or exception if error occurs.
     */
    public Mono<E> update(final E inEntity) {
        return Mono.create((Consumer<MonoSink<E>>) theMonoSink -> {
            try {
                LOGGER.info("Updating entity: {}", inEntity);

                final E theSavedEntity = mRepository.persist(inEntity);
                theMonoSink.success(theSavedEntity);
            } catch (final Throwable theException) {
                theMonoSink.error(theException);
            }
        })
            .subscribeOn(Schedulers.boundedElastic());
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
        return Mono.create((Consumer<MonoSink<E>>)  theMonoSink -> {
            try {
                LOGGER.info("Retrieving entity with id {}", inEntityId);

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
        })
            .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Finds all the entities.
     *
     * @return Flux that will receive the found entities or error.
     */
    @Transactional(readOnly = true)
    public Flux<E> findAll() {
        return Flux.create((Consumer<FluxSink<E>>) theFluxSink -> {
            try {
                LOGGER.info("Retrieving all entities.");

                final List<E> theAllEntities = mRepository.findAll();
                for (final E theEntity : theAllEntities) {
                    theFluxSink.next(theEntity);
                }
                theFluxSink.complete();
            } catch (final Throwable theException) {
                theFluxSink.error(theException);
            }
        })
            .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Deletes the entity having supplied id.
     *
     * @param inEntityId Id of entity to delete.
     * @return Mono that will receive completion or error.
     */
    public Mono<Void> delete(final Long inEntityId) {
        return Mono.create((Consumer<MonoSink<Void>>) theMonoSink -> {
            try {
                LOGGER.info("Deleting entity with id {}", inEntityId);

                mRepository.deleteById(inEntityId);
                theMonoSink.success();
            } catch (final Throwable theException) {
                theMonoSink.error(theException);
            }
        })
            .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Deletes all entities.
     *
     * @return Mono that will receive completion or error.
     */
    public Mono<Void> deleteAll() {
        return Mono.create((Consumer<MonoSink<Void>>) theMonoSink -> {
            try {
                LOGGER.info("Deleting all entities.");

                mRepository.deleteAll();
                theMonoSink.success();
            } catch (final Throwable theException) {
                theMonoSink.error(theException);
            }
        })
            .subscribeOn(Schedulers.boundedElastic());
    }
}
