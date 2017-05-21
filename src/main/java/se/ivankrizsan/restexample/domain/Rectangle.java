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
    public static final int DEFAULT_WIDTH = 10;
    public static final int DEFAULT_HEIGHT = 10;

    /* Instance variable(s): */
    @Column(name = "height", nullable = false)
    protected int mHeight = DEFAULT_HEIGHT;
    @Column(name = "width", nullable = false)
    protected int mWidth = DEFAULT_WIDTH;

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
        mHeight = inHeight;
        mWidth = inWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(final int inHeight) {
        mHeight = inHeight;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(final int inWidth) {
        mWidth = inWidth;
    }
}
