package ua.kpi.testertest.model.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.kpi.testertest.model.entity.Gist;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("tc")
@ExtendWith(SpringExtension.class)
@Testcontainers
@ContextConfiguration(initializers = {GistRepositoryTest.Initializer.class})
class GistRepositoryTest {

    @Autowired
    private GistRepository gistRepository;

    @Container
    public static PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa")
            .withExposedPorts(5432);

    @BeforeEach
    void setUp(){
        // Given
        Gist gist = new Gist();
        gist.setValidUntil(LocalDateTime.now().minusDays(1));
        gistRepository.save(gist);

        gist = new Gist();
        gist.setValidUntil(LocalDateTime.now().plusDays(1));
        gistRepository.save(gist);
    }

    @AfterEach
    void tearDown() {
        // Clean up the database after each test
        gistRepository.deleteAll();
    }

    @Test
    @Transactional
    void findAllByValidUntilLessThanOrderByValidUntil() {
        // When
        var result = gistRepository.findAllByValidUntilLessThanOrderByValidUntil(LocalDateTime.now(), Pageable.unpaged());

        // Then
        assertEquals(1, result.getTotalElements());
    }

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}