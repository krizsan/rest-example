package se.ivankrizsan.restexample.restadapter;

import org.springframework.stereotype.Component;
import se.ivankrizsan.restexample.domain.Drawing;
import se.ivankrizsan.restexample.services.DrawingService;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * REST resource exposing operations on drawings.
 *
 * @author Ivan Krizsan
 */
@Component
@Path(DrawingResource.PATH)
@Produces({MediaType.APPLICATION_JSON})
public class DrawingResource extends RestResourceBaseRxJava<Drawing> {
    /* Constant(s): */
    public static final String PATH = "/drawings";

    /**
     * Creates a REST resource using the supplied service to manipulate entities.
     *
     * @param inService Service used to manipulate entities.
     */
    public DrawingResource(final DrawingService inService) {
        setService(inService);
    }

    @Override
    protected Drawing[] entityListToArray(final List<Drawing> inEntityList) {
        return inEntityList.toArray(new Drawing[inEntityList.size()]);
    }
}
