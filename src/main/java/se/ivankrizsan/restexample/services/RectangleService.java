package se.ivankrizsan.restexample.services;

import org.springframework.stereotype.Service;
import se.ivankrizsan.restexample.domain.Rectangle;
import se.ivankrizsan.restexample.repositories.RectangleRepository;

/**
 * Service exposing operations on rectangles.
 *
 * @author Ivan Krizsan
 */
@Service
public class RectangleService extends AbstractServiceBaseRxJava<Rectangle> {

    /**
     * Creates a service instance that will use the supplied repository
     * for entity persistence.
     *
     * @param inRepository Rectangle repository.
     */
    public RectangleService(final RectangleRepository inRepository) {
        super(inRepository);
    }
}
