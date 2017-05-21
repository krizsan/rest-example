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
    public static final int DEFAULT_RADIUS = 10;

    /* Instance variable(s): */
    @Column(name = "radius", nullable = false)
    protected int mRadius = DEFAULT_RADIUS;

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
        mRadius = inRadius;
    }

    public int getRadius() {
        return mRadius;
    }

    public void setRadius(final int inRadius) {
        mRadius = inRadius;
    }
}
