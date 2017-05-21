package se.ivankrizsan.restexample.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import se.ivankrizsan.restexample.domain.Circle;
import se.ivankrizsan.restexample.helpers.CircleEntityFactory;
import se.ivankrizsan.restexample.repositories.customisation.JpaRepositoryCustomisationsImpl;

import java.util.Optional;

/**
 * Tests the JPA repository customisations implemented in
 * {@link JpaRepositoryCustomisationsImpl}.
 *
 * @author Ivan Krizsan
 */
@SpringBootTest
@EnableJpaRepositories(basePackages = {"se.ivankrizsan.restexample.repositories"},
    repositoryBaseClass = JpaRepositoryCustomisationsImpl.class)
public class RepositoryCustomisationsTest extends AbstractTestNGSpringContextTests {
    /* Constant(s): */
    public static final String UPDATED_COLOUR = "Black 2000";

    /* Instance variable(s): */
    @Autowired
    protected CircleRepository mRepository;
    protected CircleEntityFactory mEntityFactory;
    protected Circle mEntity;

    /**
     * Performs preparations before each test method.
     */
    @BeforeMethod
    public void prepareBeforeTest() {
        mEntityFactory = new CircleEntityFactory();

        final int theCreateEntityIndex = (int) Math.round(Math.random() * 100);
        mEntity = mEntityFactory.createEntity(theCreateEntityIndex);
    }

    /**
     * Tests persisting an entity that has not previously been persisted.
     * Expected outcome: The entity should be persisted and assigned an id.
     *
     * @throws Exception If error occurs. Indicates test failure.
     */
    @Test
    public void testPersistNewEntity() throws Exception {
        Circle theCircle = mRepository.persist(mEntity);
        Assert.assertNotNull(theCircle, "The entity should have been persisted");
        Assert.assertNotNull(theCircle.getId(),
            "The entity should have been assigned an id");
    }

    /**
     * Tests updating an entity that has been persisted earlier.
     * Expected outcome: The entity should be updated.
     *
     * @throws Exception If error occurs. Indicates test failure.
     */
    @Test
    public void testUpdatePersistedEntity() throws Exception {
        final Circle theCircle = mRepository.persist(mEntity);
        Assert.assertNotNull(theCircle, "The entity should have been persisted");
        Assert.assertNotNull(theCircle.getId(),
            "The entity should have been assigned an id");

        final Optional<Circle> theSameCircleOption =
            mRepository.findById(theCircle.getId());
        theSameCircleOption.get().setColour(UPDATED_COLOUR);
        mRepository.persist(theSameCircleOption.get());
        final Optional<Circle> theUpdatedCircleOption =
            mRepository.findById(theCircle.getId());
        Assert.assertTrue(theUpdatedCircleOption.isPresent(),
            "The updated entity should exist");
        Assert.assertEquals(theUpdatedCircleOption.get().getColour(), UPDATED_COLOUR,
            "The property in the entity should have been updated");
    }
}
