package ua.testtester.testertest.model.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
public class GistDTO {
    public enum Type {
        PROMO, INFO
    }

    private final UUID uuid;
    private final String author;
    private final Type type;
    private final String content;
    private final LocalDateTime validUntil;
}
