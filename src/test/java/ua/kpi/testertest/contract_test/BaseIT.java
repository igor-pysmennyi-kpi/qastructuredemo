package ua.kpi.testertest.contract_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Properties;

import static com.github.tomakehurst.wiremock.client.WireMock.create;
import static io.restassured.RestAssured.given;
import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
public class BaseIT {
    public static final String GISTS_URL = "/gist-api/gists";

    protected static WireMock holidayWiremock;

    @SneakyThrows
    @BeforeAll
    static void beforeAll() {
        Properties props = new Properties();

        props.load(ClassLoader.getSystemResourceAsStream("integration/contract-tests.properties"));
        RestAssured.port = Integer.parseInt(props.getProperty("application.port"));
        RestAssured.baseURI = props.getProperty("application.baseUri");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        holidayWiremock = create().host("localhost").port(21342).build();
    }

    @BeforeEach
    void prepare() {
        holidayWiremock.resetMappings();

        deleteAllGists();
    }

    protected void deleteAllGists() {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .delete(GISTS_URL);
    }

    protected Response createGist(String requestBody) {
        var response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .post(GISTS_URL)
                .then()
                .statusCode(HttpStatus.OK.value());

        return response.extract().response();
    }

    @SneakyThrows
    protected String getFileContent(String expectedResponseFile) {
        return IOUtils.toString(getClass().getClassLoader().getResource(expectedResponseFile), UTF_8);
    }

    @SneakyThrows
    protected String getIdFromResponse(Response response) {
        return new ObjectMapper().reader().readTree(response.getBody().asString()).get("id").asText();
    }
}
