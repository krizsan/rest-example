package se.ivankrizsan.restexample.domain;

import javax.persistence.*;

/**
 * Abstract base class for JPA entities that have a long integer id.
 *
 * @author Ivan Krizsan
 */
@MappedSuperclass
public abstract class LongIdEntity {
    /* Constant(s): */

    /* Instance variable(s): */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long inId) {
        id = inId;
    }

    @Override
    public boolean equals(final Object inOtherObject) {
        if (this == inOtherObject) {
            return true;
        }
        if (inOtherObject == null || getClass() != inOtherObject.getClass()) {
            return false;
        }

        final LongIdEntity theOtherEntity = (LongIdEntity) inOtherObject;

        return id != null ? id.equals(theOtherEntity.id) : theOtherEntity.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
