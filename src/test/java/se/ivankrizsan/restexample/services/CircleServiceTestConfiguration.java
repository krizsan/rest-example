package se.ivankrizsan.restexample.services;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import se.ivankrizsan.restexample.repositories.CircleRepository;

/**
 * Configuration for the {@code CircleServiceTest} tests.
 *
 * @author Ivan Krizsan
 */
@Configuration
@Profile("serviceTests")
public class CircleServiceTestConfiguration {
    /* Constant(s): */

    /* Dependencies: */
    @MockBean
    protected CircleRepository circleRepository;

    /**
     * Creates the service under test which will use the supplied repository for persistence.
     *
     * @param inCircleRepository Repository to be used by the service.
     * @return Service under test.
     */
    @Bean
    public CircleService serviceUnderTest(final CircleRepository inCircleRepository) {
        return new CircleService(inCircleRepository);
    }
}
