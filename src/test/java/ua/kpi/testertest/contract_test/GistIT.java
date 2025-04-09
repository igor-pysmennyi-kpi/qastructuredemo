package ua.kpi.testertest.contract_test;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

class GistIT extends BaseIT {
    @Test
    @SneakyThrows
    void testCreateGist() {
        var response = createGist(getFileContent("integration/request/create_gist.json"));

        JSONAssert.assertEquals(getFileContent("integration/request/create_gist.json"), response.getBody().asString(), JSONCompareMode.LENIENT);
    }

    @Test
    @SneakyThrows
    void testGetGist() {
        holidayWiremock.register(get(urlPathEqualTo("/v1"))//зверніть увагу на компаратор
//                .withHeader("Content-Type", equalTo("application/json")) для постів треба не забувати таке просте
//                .withRequestBody(equalToJson("{}",true, true)) не забувайте ігнори для жейсонів
                .withQueryParam("apiKey", equalTo("unique-key"))//перейменуй і покажи як падає контракт
                .withQueryParam("year", equalTo("2023"))
                .withQueryParam("month", equalTo("10"))
                .withQueryParam("day", equalTo("1"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody(getFileContent("integration/holiday-api/holiday.json"))));

        var createResponse = createGist(getFileContent("integration/request/create_gist.json"));

        var response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get(GISTS_URL + "/" + getIdFromResponse(createResponse));

        assertThat(response.getStatusCode()).isEqualTo(200);
        JSONAssert.assertEquals(getFileContent("integration/request/create_gist.json"), response.getBody().asString(), JSONCompareMode.LENIENT);
        holidayWiremock.verifyThat(getRequestedFor(urlPathEqualTo("/v1")));//можна робити верифікації, кастомні матчери і тд
    }
}
