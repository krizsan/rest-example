package se.ivankrizsan.restexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import se.ivankrizsan.restexample.repositories.customisation.JpaRepositoryCustomisationsImpl;

/**
 * Example application main class entry-point.
 *
 * @author Ivan Krizsan
 */
@SpringBootApplication
@EntityScan(basePackages = {"se.ivankrizsan.restexample.domain"})
@EnableAsync
@EnableJpaRepositories(basePackages = {"se.ivankrizsan.restexample.repositories"},
    repositoryBaseClass = JpaRepositoryCustomisationsImpl.class)
public class RestExampleApplication {

    /**
     * Application main entry-point.
     *
     * @param inArgs Command line arguments.
     */
    public static void main(String[] inArgs) {
        SpringApplication.run(RestExampleApplication.class, inArgs);
    }
}
