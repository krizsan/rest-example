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
    protected Long mId;

    public Long getId() {
        return mId;
    }

    public void setId(final Long inId) {
        mId = inId;
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

        return mId != null
            ? mId.equals(theOtherEntity.mId) : theOtherEntity.mId == null;

    }

    @Override
    public int hashCode() {
        return mId != null ? mId.hashCode() : 0;
    }
}
