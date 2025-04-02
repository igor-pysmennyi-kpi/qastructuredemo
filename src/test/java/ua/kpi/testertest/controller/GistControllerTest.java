package ua.kpi.testertest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest // Loads the complete Spring application context
@AutoConfigureMockMvc // Configures MockMvc for sending HTTP requests
@ActiveProfiles("test")
class GistControllerTest {

    @Test
    void createGist() {
    }
}