package se.ivankrizsan.restexample.restadapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import se.ivankrizsan.restexample.domain.Circle;
import se.ivankrizsan.restexample.helpers.CircleEntityFactory;
import se.ivankrizsan.restexample.repositories.CircleRepository;

/**
 * Tests the {@code CircleResource}.
 *
 * @author Ivan Krizsan
 */
public class CircleResourceTest extends RestResourceTestBase<Circle> {
    /* Constant(s): */

    /* Instance variable(s): */
    @Autowired
    protected CircleRepository mCircleRepository;

    @BeforeMethod
    @Override
    public void prepareBeforeTest() {
        mEntityFactory = new CircleEntityFactory();
        mEntityRepository = mCircleRepository;
        mResourceUrlPath = CircleResource.PATH;

        super.prepareBeforeTest();
    }
}
