package ua.kpi.testertest.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("gistapi")
@Data
public class ApplicationProperties {
    private String defaultTimeZone;
    private Integer defaultPageSize;
    private Integer networkPoolMaxSize;

    private String readTimeoutDuration;
    private String connectTimeoutDuration;

    private ExternalApiProperties holidayApi;

    @Data
    public static class ExternalApiProperties {
        private String baseUri;
        private String apiKey;
    }
}