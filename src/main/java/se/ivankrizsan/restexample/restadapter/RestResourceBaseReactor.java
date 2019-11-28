package se.ivankrizsan.restexample.restadapter;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.ivankrizsan.restexample.domain.LongIdEntity;
import se.ivankrizsan.restexample.services.AbstractServiceBaseReactor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Abstract base class for REST resources exposing operations on an entity type.
 * All operations will return HTTP status 500 with a plain text body containing an
 * error message if an error occurred during request processing.
 *
 * @param <E> Entity type.
 * @author Ivan Krizsan
 */
@RestController
public abstract class RestResourceBaseReactor<E extends LongIdEntity> {
    /* Constant(s): */

    /* Instance variable(s): */
    protected AbstractServiceBaseReactor<E> mService;

    /**
     * Retrieves all entities.
     */
    @GetMapping(produces = "application/json;charset=UTF-8")
    public Flux<E> getAll() {
        return mService.findAll();
    }

    /**
     * Deletes the entity with supplied id.
     *
     * @param inEntityId Id of entity to delete.
     * @return Void mono.
     */
    @DeleteMapping(path = "{id}", produces = "application/json;charset=UTF-8")
    public Mono<Void> deleteEntityById(
        @PathVariable("id") @NotNull final Long inEntityId) {
        return mService.delete(inEntityId);
    }

    /**
     * Deletes all entities.
     * Will return HTTP status 500 if error occurred during request processing.
     *
     * @return Void mono.
     */
    @DeleteMapping(produces = "application/json;charset=UTF-8")
    public Mono<Void> deleteAllEntities() {
        return mService.deleteAll();
    }

    /**
     * Retrieves entity with supplied id.
     *
     * @param inEntityId Id of entity to retrieve.
     * @return Mono containing entity, or empty mono if no matching entity.
     */
    @GetMapping(path = "{id}", produces = "application/json;charset=UTF-8")
    public Mono<E> getEntityById(@PathVariable("id") final Long inEntityId) {
        return mService.find(inEntityId);
    }

    /**
     * Updates the entity with supplied id by overwriting it with the supplied entity.
     *
     * @param inEntity Entity data to write.
     * @param inEntityId Id of entity to update.
     * @return Mono containing updated entity.
     */
    @PutMapping(
        path = "{id}",
        produces = "application/json;charset=UTF-8",
        consumes = "application/json")
    public Mono<E> updateEntity(@RequestBody final E inEntity,
        @PathVariable("id") @NotNull final Long inEntityId) {
        inEntity.setId(inEntityId);
        return mService.update(inEntity);
    }

    /**
     * Creates a new entity using the supplied entity data.
     *
     * @param inEntity Entity data to use when creating new entity.
     */
    @PostMapping(
        produces = "application/json;charset=UTF-8",
        consumes = "application/json")
    public Mono<E> createEntity(@RequestBody final E inEntity) {
        if (inEntity.getId() != null) {
            throw new IllegalArgumentException("A new entity must not have an id");
        }

        return mService.save(inEntity);
    }

    /**
     * Creates an array containing the entities in the supplied list.
     *
     * @param inEntityList List of entities.
     * @return Array containing the entities from the list.
     */
    protected abstract E[] entityListToArray(List<E> inEntityList);

    public AbstractServiceBaseReactor<E> getService() {
        return mService;
    }

    public void setService(final AbstractServiceBaseReactor<E> inService) {
        mService = inService;
    }
}
