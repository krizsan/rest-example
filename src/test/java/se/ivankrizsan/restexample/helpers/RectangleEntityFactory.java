package se.ivankrizsan.restexample.helpers;

import se.ivankrizsan.restexample.domain.Rectangle;

import java.awt.*;

/**
 * Entity factory that creates {@code Rectangle}s.
 *
 * @author Ivan Krizsan
 */
public class RectangleEntityFactory implements EntityFactory<Rectangle> {

    @Override
    public Rectangle createEntity(final int inIndex) {
        final Rectangle theRectangle = new Rectangle();
        theRectangle.setColour("Colour" + inIndex);
        theRectangle.setPosition(new Point(inIndex * 15, inIndex * 20));
        theRectangle.setHeight(inIndex * 7);
        theRectangle.setWidth(inIndex * 8);
        return theRectangle;
    }
}
