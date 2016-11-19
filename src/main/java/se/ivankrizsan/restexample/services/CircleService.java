package se.ivankrizsan.restexample.services;

import org.springframework.stereotype.Service;
import se.ivankrizsan.restexample.domain.Circle;
import se.ivankrizsan.restexample.repositories.CircleRepository;

/**
 * Service exposing operations on circles.
 *
 * @author Ivan Krizsan
 */
@Service
public class CircleService extends AbstractServiceBaseRxJava<Circle> {

    /**
     * Creates a service instance that will use the supplied repository
     * for entity persistence.
     *
     * @param inRepository Circle repository.
     */
    public CircleService(final CircleRepository inRepository) {
        super(inRepository);
    }
}
