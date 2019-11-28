package se.ivankrizsan.restexample.restadapter;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import se.ivankrizsan.restexample.domain.Rectangle;
import se.ivankrizsan.restexample.services.RectangleService;

import java.util.List;

/**
 * REST resource exposing operations on rectangles.
 *
 * @author Ivan Krizsan
 */
@Component
@RequestMapping(path = RectangleResource.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class RectangleResource extends RestResourceBaseReactor<Rectangle> {
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
        return inEntityList.toArray(new Rectangle[0]);
    }
}
