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
/* Json type info style, using fully qualified class names, is not optimal from a security point of view. */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public abstract class Shape extends LongIdEntity {
    /* Constant(s): */

    /* Instance variable(s): */
    @Column(name = "colour", nullable = false)
    protected String colour;
    @Column(name = "position", nullable = false)
    protected Point position;

    public String getColour() {
        return colour;
    }

    public void setColour(final String inColour) {
        colour = inColour;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(final Point inPosition) {
        position = inPosition;
    }
}
