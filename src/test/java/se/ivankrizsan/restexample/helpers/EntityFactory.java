package se.ivankrizsan.restexample.helpers;

/**
 * Interface that defines the properties of an entity factory that is used in tests
 * to create instances of entities.
 * The id of entities created by an entity factory is never set.
 *
 * @author Ivan Krizsan
 * @param <E> Type of entity the factory will created.
 */
public interface EntityFactory<E> {
    /**
     * Creates a new entity instance setting all its properties.
     * The supplied index may, if appropriate, be used to compose values when
     * setting the entity properties.
     *
     * @param inIndex Index for property values.
     * @return New entity instance.
     */
    E createEntity(int inIndex);
}
