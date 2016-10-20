package se.ivankrizsan.restexample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;
import se.ivankrizsan.restexample.repositories.customisation.JpaRepositoryCustomisationsImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableJpaRepositories(basePackages = {"se.ivankrizsan.restexample.repositories"},
    repositoryBaseClass = JpaRepositoryCustomisationsImpl.class)
public class RestExampleApplicationTests {

    @Test
    public void contextLoads() {
    }

}
