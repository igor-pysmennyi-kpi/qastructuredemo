package ua.kpi.testertest.model.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Jacksonized
@Builder(builderClassName = "GistDTOBuilder", toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(creatorVisibility = JsonAutoDetect.Visibility.ANY)
public class GistDTO {
    public enum Type {
        PROMO, INFO
    }

    UUID id;
    String author;
    Type type;
    String content;
    LocalDateTime validFrom;
    LocalDateTime validUntil;

    //додали час, коли з сервера дістали, раптом хтось ще поедітає
    LocalDateTime retrievedTime;

}
