package se.ivankrizsan.restexample.restadapter;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import se.ivankrizsan.restexample.domain.Circle;
import se.ivankrizsan.restexample.services.CircleService;

import java.util.List;

/**
 * REST resource exposing operations on circles.
 *
 * @author Ivan Krizsan
 */
@Component
@RequestMapping(path = CircleResource.PATH)
public class CircleResource extends RestResourceBaseReactor<Circle> {
    /* Constant(s): */
    public static final String PATH = "/circles";

    /**
     * Creates a REST resource using the supplied service to manipulate entities.
     *
     * @param inService Service used to manipulate entities.
     */
    public CircleResource(final CircleService inService) {
        setService(inService);
    }

    @Override
    protected Circle[] entityListToArray(final List<Circle> inEntityList) {
        return inEntityList.toArray(new Circle[0]);
    }
}
