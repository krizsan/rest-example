package se.ivankrizsan.restexample.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.awt.*;

/**
 * Abstract base class for shapes in a drawing.
 *
 * @author Ivan Krizsan
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
/* Need to include type information since there are collections that contain shapes. */
@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS,
    include = JsonTypeInfo.As.PROPERTY, property = "shapeType")
public abstract class Shape extends LongIdEntity {
    /* Constant(s): */

    /* Instance variable(s): */
    @Column(name = "colour", nullable = false)
    protected String mColour;
    @Column(name = "position", nullable = false)
    protected Point mPosition;

    public String getColour() {
        return mColour;
    }

    public void setColour(final String inColour) {
        mColour = inColour;
    }

    public Point getPosition() {
        return mPosition;
    }

    public void setPosition(final Point inPosition) {
        mPosition = inPosition;
    }
}
