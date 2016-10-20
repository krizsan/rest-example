package se.ivankrizsan.restexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Example application main class entry-point.
 *
 * @author Ivan Krizsan
 */
@SpringBootApplication
@EntityScan(basePackages = { "se.ivankrizsan.restexample.domain" })
@EnableAsync
public class RestExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestExampleApplication.class, args);
	}
}
