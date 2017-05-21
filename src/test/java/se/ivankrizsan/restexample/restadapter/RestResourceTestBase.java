package se.ivankrizsan.restexample.restadapter;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import se.ivankrizsan.restexample.domain.LongIdEntity;
import se.ivankrizsan.restexample.helpers.EntityFactory;
import se.ivankrizsan.restexample.helpers.JsonConverter;
import se.ivankrizsan.restexample.repositories.customisation.JpaRepositoryCustomisationsImpl;

import java.io.IOException;
import java.util.Optional;


/**
 * Abstract base class for tests of REST resources.
 * Only JSON representation is used in the tests.
 *
 * @author Ivan Krizsan
 * @param <E> Type of entity which REST resource to test.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableJpaRepositories(basePackages = {"se.ivankrizsan.restexample.repositories"},
    repositoryBaseClass = JpaRepositoryCustomisationsImpl.class)
public abstract class RestResourceTestBase<E extends LongIdEntity> extends
    AbstractTestNGSpringContextTests {
    /* Constant(s): */
    protected static final int ENDPOINT_PORT = 8080;
    protected static final long TEST_TIMEOUT = 30000;

    /* Instance variable(s): */
    protected EntityFactory<E> mEntityFactory;
    protected CrudRepository<E, Long> mEntityRepository;
    protected String mResourceUrlPath;
    protected E mExpectedEntity;
    protected int mCreateEntityIndex;

    /**
     * Sets up RestAssured test framework before tests.
     */
    @BeforeTest
    public void setUpRestAssured() {
        RestAssured.reset();
        RestAssured.port = ENDPOINT_PORT;
        RestAssured.basePath = "";
    }

    /**
     * Performs preparations before each test method.
     * Creates and persists one entity before running each test method.
     */
    @BeforeMethod
    public void prepareBeforeTest() {
        mCreateEntityIndex = (int) Math.round(Math.random() * 100);
        final E theEntity = mEntityFactory.createEntity(mCreateEntityIndex);
        mExpectedEntity = mEntityRepository.save(theEntity);

        Assert.assertNotNull(mExpectedEntity);
        Assert.assertNotNull(mExpectedEntity.getId());
    }

    /**
     * Tests retrieving one entity.
     * An entity should be retrieved and the properties of the entity should have the
     * same values as the entity persisted before the test.
     *
     * @throws IOException If error occurs. Indicates test failure.
     */
    @Test(timeOut = TEST_TIMEOUT)
    public void testGetEntity() throws IOException {
        final Response theResponse = RestAssured
            .given()
            .contentType("application/json")
            .accept("application/json")
            .when()
            .get(mResourceUrlPath + "/" + mExpectedEntity.getId());
        final String theResponseJson = theResponse.prettyPrint();
        theResponse
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);

        final Object theRetrievedEntity = JsonConverter.jsonToObject(
            theResponseJson, mExpectedEntity.getClass());
        ReflectionAssert.assertLenientEquals(
            "Retrieved entity should have the correct property values",
            mExpectedEntity, theRetrievedEntity);
    }

    /**
     * Tests deletion of one entity.
     * This test does not verify deletion of contained entities
     * to which the delete operation is to be cascaded.
     * The entity should have been deleted.
     *
     * @throws IOException If error occurs. Indicates test failure.
     */
    @Test(timeOut = TEST_TIMEOUT)
    public void testDeleteEntity() throws IOException {
        RestAssured
            .given()
            .when()
            .delete(mResourceUrlPath + "/" + mExpectedEntity.getId())
            .then()
            .statusCode(200);

        final Optional<E> thePersistedEntityAfterDeleteOptional =
            mEntityRepository.findById(mExpectedEntity.getId());
        Assert.assertFalse(thePersistedEntityAfterDeleteOptional.isPresent(),
            "Entity should have been deleted");
    }

    /**
     * Tests deletion of all entities.
     * This test does not verify deletion of contained entities
     * to which the delete operation is to be cascaded.
     * All entities should have been deleted.
     *
     * @throws IOException If error occurs. Indicates test failure.
     */
    @Test(timeOut = TEST_TIMEOUT)
    public void testDeleteAllEntities() throws IOException {
        RestAssured
            .given()
            .when()
            .delete(mResourceUrlPath)
            .then()
            .statusCode(200);

        final Iterable<E> thePersistedEntitiesAfterDelete =
            mEntityRepository.findAll();
        Assert.assertFalse(thePersistedEntitiesAfterDelete.iterator().hasNext(),
            "All entities should have been deleted");
    }

    /**
     * Tests creation of one entity.
     * An entity should be created and the properties of the entity should have the
     * same values as the properties in the entity representation sent to
     * the service.
     *
     * @throws Exception If error occurs. Indicates test failure.
     */
    @Test(timeOut = TEST_TIMEOUT)
    public void testCreateEntity() throws Exception {
        mEntityRepository.deleteAll();
        final E theExpectedEntity = mEntityFactory.createEntity(1);
        final String theJsonRepresentation =
            JsonConverter.objectToJson(theExpectedEntity);
        final Response theResponse = RestAssured
            .given()
            .contentType("application/json")
            .accept("application/json")
            .body(theJsonRepresentation)
            .when()
            .post(mResourceUrlPath);
        final String theCreatedEntityJson = theResponse.prettyPrint();
        theResponse
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);

        final LongIdEntity theCreatedEntity =
            JsonConverter.jsonToObject(
                theCreatedEntityJson, theExpectedEntity.getClass());
        /*
         * Id will be null in new entity, need to set id so comparision
         * do not fail due to this.
         */
        theExpectedEntity.setId(theCreatedEntity.getId());
        ReflectionAssert.assertLenientEquals(
            "Created entity should have the correct property values",
            theExpectedEntity, theCreatedEntity);
    }

    /**
     * Tests updating one entity.
     * An updated entity should be returned.
     *
     * @throws Exception If error occurs. Indicates test failure.
     */
    @Test(timeOut = TEST_TIMEOUT)
    public void testUpdateEntity() throws Exception {
        final Long theExistingEntityId = mExpectedEntity.getId();
        final E theExpectedEntity =
            mEntityFactory.createEntity(mCreateEntityIndex + 1);
        theExpectedEntity.setId(theExistingEntityId);
        final String theJsonRepresentation =
            JsonConverter.objectToJson(theExpectedEntity);
        final Response theResponse = RestAssured
            .given()
            .contentType("application/json")
            .accept("application/json")
            .body(theJsonRepresentation)
            .when()
            .put(mResourceUrlPath + "/" + mExpectedEntity.getId());
        final String theResponseJson = theResponse.prettyPrint();
        theResponse
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);

        final Object theUpdatedEntity = JsonConverter.jsonToObject(
            theResponseJson, mExpectedEntity.getClass());
        ReflectionAssert.assertLenientEquals(
            "Updated entity should have the correct property values",
            theExpectedEntity, theUpdatedEntity);
    }

    /**
     * Tests updating an entity that has not previously been persisted.
     * The update should fail and no entity should be persisted.
     *
     * @throws Exception If error occurs. Indicates test failure.
     */
    @Test(timeOut = TEST_TIMEOUT)
    public void testUpdateEntityNotPersisted() throws Exception {
        final long theEntityCountBefore = mEntityRepository.count();

        final E theExpectedEntity =
            mEntityFactory.createEntity(mCreateEntityIndex + 1);
        final String theJsonRepresentation =
            JsonConverter.objectToJson(theExpectedEntity);
        final Response theResponse = RestAssured
            .given()
            .contentType("application/json")
            .accept("application/json")
            .body(theJsonRepresentation)
            .when()
            .put(mResourceUrlPath + "/" + mExpectedEntity.getId() + 1);

        theResponse
            .then()
            .statusCode(500)
            .contentType(ContentType.TEXT);

        final long theEntityCountAfter = mEntityRepository.count();
        Assert.assertEquals(theEntityCountAfter, theEntityCountBefore,
            "Number of entities should be unchanged");
    }
}
