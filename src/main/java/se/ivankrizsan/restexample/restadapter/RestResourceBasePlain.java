package se.ivankrizsan.restexample.restadapter;

import se.ivankrizsan.restexample.domain.LongIdEntity;
import se.ivankrizsan.restexample.services.AbstractServiceBasePlain;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.function.Supplier;

/**
 * Abstract base class for REST resources exposing operations on an entity type.
 * All operations will return HTTP status 500 with a plain text body containing an
 * error message if an error occurred during request processing.
 *
 * @param <E> Entity type.
 * @author Ivan Krizsan
 */
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
@Consumes({MediaType.APPLICATION_JSON})
public abstract class RestResourceBasePlain<E extends LongIdEntity> {
    /* Constant(s): */

    /* Instance variable(s): */
    protected AbstractServiceBasePlain<E> mService;


    /**
     * Retrieves all entities.
     *
     * @return HTTP response object with HTTP status 200 if operation succeeded or
     * HTTP error status code and a plain-text error message if an error occurred.
     */
    @GET
    public Response getAll() {
        return performServiceOperation(
            () -> {
                final List<E> theEntitiesList = mService.findAll();
                final E[] theEntitiesArray = entityListToArray(theEntitiesList);
                return Response.ok().entity(theEntitiesArray).build();
            },
            500, "An error occurred retrieving all entities: "
        );
    }

    /**
     * Deletes the entity with supplied id.
     *
     * @param inEntityId Id of entity to delete.
     * @return HTTP response object with HTTP status 200 if operation succeeded or
     * HTTP error status code and a plain-text error message if an error occurred.
     */
    @DELETE
    @Path("{id}")
    public Response deleteEntityById(@PathParam("id") @NotNull final Long inEntityId) {
        return performServiceOperation(
            () -> {
                mService.delete(inEntityId);
                return Response.ok().build();
            },
            500, "An error occurred deleting entity with id " + inEntityId + ": "
        );
    }

    /**
     * Deletes all entities.
     * Will return HTTP status 500 if error occurred during request processing.
     *
     * @return HTTP response object with HTTP status 200 if operation succeeded or
     * HTTP error status code and a plain-text error message if an error occurred.
     */
    @DELETE
    public Response deleteAllEntities() {
        return performServiceOperation(
            () -> {
                mService.deleteAll();
                return Response.ok().build();
            },
            500, "An error occurred deleting all entities: "
        );
    }

    /**
     * Retrieves entity with supplied id.
     *
     * @param inEntityId Id of entity to retrieve.
     * @return HTTP response object with HTTP status 200 if operation succeeded or
     * HTTP error status code and a plain-text error message if an error occurred.
     */
    @GET
    @Path("{id}")
    public Response getEntityById(@PathParam("id") Long inEntityId) {
        return performServiceOperation(
            () -> {
                final E theEntity = mService.find(inEntityId);
                return Response.ok(theEntity).build();
            },
            500, "An error occurred finding entity with id " + inEntityId + ": "
        );
    }

    /**
     * Updates the entity with supplied id by overwriting it with the supplied entity.
     *
     * @param inEntity Entity data to write.
     * @param inEntityId Id of entity to update.
     * @return HTTP response object with HTTP status 200 if operation succeeded or
     * HTTP error status code and a plain-text error message if an error occurred.
     */
    @PUT
    @Path("{id}")
    public Response updateEntity(final E inEntity, @PathParam("id") @NotNull final Long inEntityId) {
        final Response theResponse = performServiceOperation(
            () -> {
                inEntity.setId(inEntityId);
                final E theEntity = mService.update(inEntity);
                return Response.ok(theEntity).build();
            },
            500, "An error occurred updating entity with id " + inEntityId + ": "
        );
        return theResponse;
    }

    /**
     * Creates a new entity using the supplied entity data.
     *
     * @param inEntity Entity data to use when creating new entity.
     * @return HTTP response object with HTTP status 200 containing entity representation
     * if operation succeeded or HTTP error status code and a plain-text error message
     * if an error occurred.
     */
    @POST
    public Response createEntity(final E inEntity) {
        return performServiceOperation(
            () -> {
                Response theResponse;
                if (inEntity.getId() != null) {
                    theResponse = Response.status(400).entity("Id must not be set on new entity").build();
                } else {
                    final E theEntity = mService.save(inEntity);
                    theResponse = Response.ok(theEntity).build();
                }
                return theResponse;
            },
            500, "An error occurred creating a new entity: "
        );
    }

    /**
     * Performs the operation as defined by the supplied response supplier.
     * If the operation completes without errors, the response being the result of the operation
     * is returned as result of this method.
     * If an error occurs during the operation, an error response is created with the
     * supplied HTTP error status and the supplied plain-text error message with the message
     * from the exception that occurred appended.
     *
     * @param inResponseSupplier Operation to complete.
     * @param inErrorHttpStatus HTTP status to set in response in case of error.
     * @param inErrorMessage Plain-text error message to set in response in case of error.
     * @return Response object.
     */
    protected static Response performServiceOperation(final Supplier<Response> inResponseSupplier,
        final int inErrorHttpStatus, final String inErrorMessage) {
        Response theResponse;
        try {
            theResponse = inResponseSupplier.get();
        } catch (final Throwable theException) {
            theResponse = Response.
                status(inErrorHttpStatus).
                entity(inErrorMessage + theException.getMessage()).
                type(MediaType.TEXT_PLAIN_TYPE).
                build();
        }
        return theResponse;
    }

    /**
     * Creates an array containing the entities in the supplied list.
     *
     * @param inEntityList List of entities.
     * @return Array containing the entities from the list.
     */
    protected abstract E[] entityListToArray(final List<E> inEntityList);

    public AbstractServiceBasePlain<E> getService() {
        return mService;
    }

    public void setService(final AbstractServiceBasePlain<E> inService) {
        mService = inService;
    }
}
