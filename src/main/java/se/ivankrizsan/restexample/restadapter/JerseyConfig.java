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
        /* All resource classes are to be located in the same package as this class. */
        packages(this.getClass().getPackage().getName());
    }
}
