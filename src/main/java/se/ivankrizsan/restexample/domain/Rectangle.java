package se.ivankrizsan.restexample.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Represents a rectangle shape with a given height and width.
 *
 * @author Ivan Krizsan
 */
@Entity(name = "Rectangle")
public class Rectangle extends Shape {
    /* Constant(s): */
    public final static int DEFAULT_WIDTH = 10;
    public final static int DEFAULT_HEIGHT = 10;

    /* Instance variable(s): */
    @Column(name = "height", nullable = false)
    protected int height = DEFAULT_HEIGHT;
    @Column(name = "width", nullable = false)
    protected int width = DEFAULT_WIDTH;

    /**
     * Default constructor.
     */
    public Rectangle() {
    }

    /**
     * Creates a rectangle with the supplied height and width.
     *
     * @param inHeight Height of new rectangle.
     * @param inWidth Width of new rectangle.
     */
    public Rectangle(final int inHeight, final int inWidth) {
        height = inHeight;
        width = inWidth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(final int inHeight) {
        height = inHeight;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(final int inWidth) {
        width = inWidth;
    }
}
