package se.ivankrizsan.restexample.helpers;

import se.ivankrizsan.restexample.domain.Circle;

import java.awt.*;

/**
 * Entity factory that creates {@code Circle} instances.
 *
 * @author Ivan Krizsan
 */
public class CircleEntityFactory implements EntityFactory<Circle> {

    @Override
    public Circle createEntity(final int inIndex) {
        final Circle theCircle = new Circle(inIndex * 10);
        theCircle.setColour("Colour" + inIndex);
        theCircle.setPosition(new Point(inIndex * 15, inIndex * 20));
        return theCircle;
    }
}
