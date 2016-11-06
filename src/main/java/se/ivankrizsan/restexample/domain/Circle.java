package se.ivankrizsan.restexample.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Represents a circle shape with a given radius.
 *
 * @author Ivan Krizsan
 */
@Entity(name = "Circle")
public class Circle extends Shape {
    /* Constant(s): */
    public final static int DEFAULT_RADIUS = 10;

    /* Instance variable(s): */
    @Column(name = "radius", nullable = false)
    protected int radius = DEFAULT_RADIUS;

    /**
     * Default constructor.
     */
    public Circle() {
    }

    /**
     * Creates a circle with the supplied radius.
     *
     * @param inRadius Radius of the new circle.
     */
    public Circle(final int inRadius) {
        radius = inRadius;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(final int inRadius) {
        radius = inRadius;
    }
}
