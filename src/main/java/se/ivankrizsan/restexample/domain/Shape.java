package se.ivankrizsan.restexample.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.awt.*;

/**
 * Abstract base class for shapes in a drawing.
 *
 * @author Ivan Krizsan
 */
@Entity
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
