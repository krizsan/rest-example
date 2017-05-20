package se.ivankrizsan.restexample.restadapter;

import org.springframework.stereotype.Component;
import se.ivankrizsan.restexample.domain.Rectangle;
import se.ivankrizsan.restexample.services.RectangleService;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * REST resource exposing operations on rectangles.
 *
 * @author Ivan Krizsan
 */
@Component
@Path(RectangleResource.PATH)
@Produces({MediaType.APPLICATION_JSON})
public class RectangleResource extends RestResourceBaseRxJava<Rectangle> {
    /* Constant(s): */
    public static final String PATH = "/rectangles";

    /**
     * Creates a REST resource using the supplied service to manipulate entities.
     *
     * @param inService Service used to manipulate entities.
     */
    public RectangleResource(final RectangleService inService) {
        setService(inService);
    }

    @Override
    protected Rectangle[] entityListToArray(final List<Rectangle> inEntityList) {
        return inEntityList.toArray(new Rectangle[inEntityList.size()]);
    }
}
