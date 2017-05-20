package se.ivankrizsan.restexample.restadapter;

import org.springframework.stereotype.Component;
import se.ivankrizsan.restexample.domain.Circle;
import se.ivankrizsan.restexample.services.CircleService;

import javax.ws.rs.Path;
import java.util.List;

/**
 * REST resource exposing operations on circles.
 *
 * @author Ivan Krizsan
 */
@Component
@Path(CircleResource.PATH)
public class CircleResource extends RestResourceBaseRxJava<Circle> {
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
        return inEntityList.toArray(new Circle[inEntityList.size()]);
    }
}
