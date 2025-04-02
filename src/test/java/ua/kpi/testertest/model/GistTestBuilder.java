package ua.kpi.testertest.model;

import lombok.experimental.UtilityClass;
import ua.kpi.testertest.model.dto.GistDTO;
import ua.kpi.testertest.model.entity.Gist;
import ua.kpi.testertest.model.entity.Type;

import java.time.LocalDateTime;
import java.util.UUID;

@UtilityClass
public class GistTestBuilder {
    public static final UUID GIST_ID = UUID.fromString("7988d77a-c6cf-4258-a262-bce6d2beb5e0");
    public static final String GIST_AUTHOR = "author";
    public static final Type GIST_TYPE = Type.INFO;
    public static final GistDTO.Type GIST_TYPE_DTO = GistDTO.Type.INFO;
    public static final String GIST_CONTENT = "some description";
    public static final LocalDateTime GIST_VALID_FROM = LocalDateTime.of(2021, 12, 30, 23, 59, 59);
    public static final LocalDateTime GIST_VALID_UNTIL = LocalDateTime.of(2021, 12, 31, 23, 59, 59);

    public static Gist aGist() {
        var gist = new Gist();
        gist.setId(GIST_ID);
        gist.setAuthor(GIST_AUTHOR);
        gist.setType(GIST_TYPE);
        gist.setContent(GIST_CONTENT);
        gist.setValidFrom(GIST_VALID_FROM);
        gist.setValidUntil(GIST_VALID_UNTIL);
        return gist;
    }


    public static GistDTO aGistDto() {
        return GistDTO.builder()
                .id(GIST_ID)
                .author(GIST_AUTHOR)
                .type(GIST_TYPE_DTO)
                .content(GIST_CONTENT)
                .validFrom(GIST_VALID_FROM)
                .validUntil(GIST_VALID_UNTIL)
                .build();
    }

}
