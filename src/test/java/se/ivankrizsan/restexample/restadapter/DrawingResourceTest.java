package se.ivankrizsan.restexample.restadapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import se.ivankrizsan.restexample.domain.Drawing;
import se.ivankrizsan.restexample.helpers.DrawingEntityFactory;
import se.ivankrizsan.restexample.repositories.DrawingRepository;

/**
 * Tests the {@code DrawingResource}.
 *
 * @author Ivan Krizsan
 */
public class DrawingResourceTest extends RestResourceTestBase<Drawing> {
    /* Constant(s): */

    /* Instance variable(s): */
    @Autowired
    protected DrawingRepository mDrawingRepository;

    @BeforeMethod
    @Override
    public void prepareBeforeTest() {
        mEntityFactory = new DrawingEntityFactory();
        mEntityRepository = mDrawingRepository;
        mResourceUrlPath = DrawingResource.PATH;

        super.prepareBeforeTest();
    }
}
