package se.ivankrizsan.restexample.restadapter;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * Jersey configuration.
 * Cannot rely on classpath scanning, due to a bug in Jersey making it unable
 * to scan nested JAR-files.
 *
 * @author Ivan Krizsan
 */
@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(CircleResource.class);
        register(RectangleResource.class);
        register(DrawingResource.class);
    }
}
