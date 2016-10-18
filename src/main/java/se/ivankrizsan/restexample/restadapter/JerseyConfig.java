package se.ivankrizsan.restexample.restadapter;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * Jersey configuration.
 *
 * @author Ivan Krizsan
 */
@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(CircleResource.class);
        register(DrawingResource.class);
        register(RectangleResource.class);
    }
}
