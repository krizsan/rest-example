package se.ivankrizsan.restexample.helpers;

import se.ivankrizsan.restexample.domain.Drawing;
import se.ivankrizsan.restexample.domain.Shape;

import java.util.Date;

/**
 * Entity factory that creates {@code Drawing} instances.
 *
 * @author Ivan Krizsan
 */
public class DrawingEntityFactory implements EntityFactory<Drawing> {
    /* Constant(s): */

    /* Instance variable(s): */
    protected CircleEntityFactory mCircleEntityFactory = new CircleEntityFactory();
    protected RectangleEntityFactory mRectangleEntityFactory = new RectangleEntityFactory();

    @Override
    public Drawing createEntity(final int inIndex) {
        Shape theShape;

        final Drawing theDrawing = new Drawing();
        theDrawing.setCreationDate(new Date());
        theDrawing.setName("DrawingName" + inIndex);

        theShape = mCircleEntityFactory.createEntity(inIndex * 2);
        theDrawing.addShape(theShape);
        theShape = mRectangleEntityFactory.createEntity(inIndex * 3);
        theDrawing.addShape(theShape);
        theShape = mRectangleEntityFactory.createEntity(inIndex * 4);
        theDrawing.addShape(theShape);

        return theDrawing;
    }
}
