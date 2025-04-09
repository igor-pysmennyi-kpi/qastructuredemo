package ua.kpi.testertest.common;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ua.kpi.testertest.common.holiday.restclient.HolidayRestClient;

import java.time.Duration;

@Configuration
public class RestConfig {
    @Bean
    public RestTemplate restTemplate(ApplicationProperties applicationProperties, RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .readTimeout(Duration.parse(applicationProperties.getReadTimeoutDuration()))
                .connectTimeout(Duration.parse(applicationProperties.getConnectTimeoutDuration()))
                .build();
    }

    @Bean
    public HolidayRestClient holidayRestClient(ApplicationProperties applicationProperties, RestTemplate restTemplate) {
        return new HolidayRestClient(
                restTemplate,
                applicationProperties.getHolidayApi().getBaseUri(),
                applicationProperties.getHolidayApi().getApiKey()
        );
    }
}
