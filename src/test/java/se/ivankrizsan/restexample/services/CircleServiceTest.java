package se.ivankrizsan.restexample.services;

import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import se.ivankrizsan.restexample.domain.Circle;
import se.ivankrizsan.restexample.repositories.CircleRepository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import java.awt.*;
import java.util.Optional;

/**
 * Tests of the {@code CircleService}.
 * This test-class contains mainly negative tests.
 *
 * @author Ivan Krizsan
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CircleServiceTestConfiguration.class)
@ActiveProfiles("serviceTests")
public class CircleServiceTest extends AbstractTestNGSpringContextTests {
    /* Constant(s): */

    /* Instance variable(s): */
    @Autowired
    protected CircleService mServiceUnderTest;
    @Autowired
    protected CircleRepository mServiceRepository;

    /**
     * Tests saving a circle entity using the circle service when there is an error occurring
     * in the circle repository.
     * Expected result:
     * There should be a mono returned from the service.
     * There should be an error from the mono containing a persistence exception.
     */
    @Test
    public void saveCircleWithPersistenceErrorTest() {
        /* Set up repository mock as to generate error when persistence is attempted. */
        Mockito
            .when(mServiceRepository.save(ArgumentMatchers.isNotNull()))
            .thenThrow(PersistenceException.class);

        /* Create the entity to save. */
        final Circle theCircle = new Circle();
        theCircle.setRadius(10);
        theCircle.setColour("Blue");
        theCircle.setPosition(new Point());

        /* Attempt the save. */
        final Mono<Circle> theSavedCircleMono = mServiceUnderTest.save(theCircle);

        /* Verify that there is an error from the returned mono containing an exception . */
        StepVerifier
            .create(theSavedCircleMono)
            .expectError(PersistenceException.class)
            .verify();
    }

    /**
     * Tests finding a circle entity using an id for which there is no entity.
     * Expected result:
     * There should be a mono returned from the service.
     * There should be an error from the mono containing an entity not found exception.
     */
    @Test
    public void findByIdNoMatchingIdTest() {
        /* Set up repository mock as to return no entity when a find-by-id is attempted. */
        Mockito
            .when(mServiceRepository.findById(ArgumentMatchers.anyLong()))
            .thenReturn(Optional.empty());

        /* Attempt the find. */
        final Mono<Circle> theFoundCircleMono = mServiceUnderTest.find(1L);

        /* Verify that there is an error from the returned mono containing an exception . */
        StepVerifier
            .create(theFoundCircleMono)
            .expectError(EntityNotFoundException.class)
            .verify();
    }

    /**
     * Tests finding a circle entity when there is an error occurring in the repository.
     * Expected result:
     * There should be a mono returned from the service.
     * There should be an error from the mono containing an entity not found exception.
     */
    @Test
    public void findByIdPersistenceErrorTest() {
        /* Set up repository mock as to generate error when find is attempted. */
        Mockito
            .when(mServiceRepository.findById(ArgumentMatchers.anyLong()))
            .thenThrow(PersistenceException.class);

        /* Attempt the find. */
        final Mono<Circle> theFoundCircleMono = mServiceUnderTest.find(1L);

        /* Verify that there is an error from the returned mono containing an exception . */
        StepVerifier
            .create(theFoundCircleMono)
            .expectError(PersistenceException.class)
            .verify();
    }

    /**
     * Tests finding all circle entities when there is an error occurring in the repository.
     * Expected result:
     * There should be a flux returned from the service.
     * There should be an error from the mono containing an entity not found exception.
     */
    @Test
    public void findAllPersistenceErrorTest() {
        /* Set up repository mock as to generate error when find is attempted. */
        Mockito
            .when(mServiceRepository.findAll())
            .thenThrow(PersistenceException.class);

        /* Attempt the find. */
        final Flux<Circle> theFoundCirclesFlux = mServiceUnderTest.findAll();

        /* Verify that there is an error from the returned mono containing an exception . */
        StepVerifier
            .create(theFoundCirclesFlux)
            .expectError(PersistenceException.class)
            .verify();
    }

    /**
     * Tests deleting all circle entities when there is an error occurring in the repository.
     * Expected result:
     * There should be a mono returned from the service.
     * There should be an error from the mono containing a persistence exception.
     */
    @Test
    public void deleteAllPersistenceErrorTest() {
        /* Set up repository mock as to generate error when delete all is attempted. */
        Mockito
            .doThrow(PersistenceException.class)
            .when(mServiceRepository).deleteAll();

        /* Attempt the delete. */
        final Mono<Void> theDeleteAllMono = mServiceUnderTest.deleteAll();

        /* Verify that there is an error from the returned mono containing an exception . */
        StepVerifier
            .create(theDeleteAllMono)
            .expectError(PersistenceException.class)
            .log()
            .verify();
    }

    /**
     * Tests deleting a circle entity when there is an error occurring in the repository.
     * Expected result:
     * There should be a mono returned from the service.
     * There should be an error from the mono containing an entity not found exception.
     */
    @Test
    public void deleteByIdPersistenceErrorTest() {
        /* Set up repository mock as to generate error when find is attempted. */
        Mockito
            .doThrow(PersistenceException.class)
            .when(mServiceRepository).deleteById(ArgumentMatchers.anyLong());

        /* Attempt the delete. */
        final Mono<Void> theDeleteMono = mServiceUnderTest.delete(1L);

        /* Verify that there is an error from the returned mono containing an exception . */
        StepVerifier
            .create(theDeleteMono)
            .expectError(PersistenceException.class)
            .verify();
    }
}
