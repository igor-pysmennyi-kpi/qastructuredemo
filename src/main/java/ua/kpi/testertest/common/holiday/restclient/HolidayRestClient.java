package ua.kpi.testertest.common.holiday.restclient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ua.kpi.testertest.common.holiday.model.HolidayAbstractApiDTO;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class HolidayRestClient {
    private static final String ENTITY_PATH = "/v1";

    private static final String COUNTRY = "UA";

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String apiKey;

    public List<HolidayAbstractApiDTO> getHolidays(LocalDate date) {
        var query = UriComponentsBuilder.fromUriString(baseUrl)
                .path(ENTITY_PATH)
                .queryParam("apiKey", apiKey)
                .queryParam("country", COUNTRY)
                .queryParam("year", date.getYear())
                .queryParam("month", date.getMonthValue())
                .queryParam("day", date.getDayOfMonth())
                .queryParam("apiKey", apiKey);

        var response = restTemplate
                .exchange(query.build().encode().toUri(),
                        HttpMethod.GET,
                        RequestEntity.EMPTY,
                        new ParameterizedTypeReference<List<HolidayAbstractApiDTO>>() {
                        });
        return response.getBody();
    }
}