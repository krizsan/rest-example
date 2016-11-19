package se.ivankrizsan.restexample.restadapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import se.ivankrizsan.restexample.domain.Rectangle;
import se.ivankrizsan.restexample.helpers.RectangleEntityFactory;
import se.ivankrizsan.restexample.repositories.RectangleRepository;

/**
 * Tests the {@code RectangleResource}.
 *
 * @author Ivan Krizsan
 */
public class RectangleResourceTest extends RestResourceTestBase<Rectangle> {
    /* Constant(s): */

    /* Instance variable(s): */
    @Autowired
    protected RectangleRepository mRectangleRepository;

    @BeforeMethod
    @Override
    public void prepareBeforeTest() {
        mEntityFactory = new RectangleEntityFactory();
        mEntityRepository = mRectangleRepository;
        mResourceUrlPath = RectangleResource.PATH;

        super.prepareBeforeTest();
    }
}
