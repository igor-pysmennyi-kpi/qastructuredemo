package ua.testtester.testertest.model.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder(builderClassName = "GistDTOBuilder", toBuilder = true)
@JsonDeserialize(builder = GistDTO.GistDTOBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(creatorVisibility = JsonAutoDetect.Visibility.ANY)
public class GistDTO {
    public enum Type {
        PROMO, INFO
    }

    private final UUID uuid;
    private final String author;
    private final Type type;
    private final String content;
    private final LocalDateTime validUntil;

    @JsonPOJOBuilder(withPrefix = "")
    public static class GistDTOBuilder {
    }
}
