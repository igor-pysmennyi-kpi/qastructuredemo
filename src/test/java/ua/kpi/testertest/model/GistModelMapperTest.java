package ua.kpi.testertest.model;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ua.kpi.testertest.model.dto.GistDTO;
import ua.kpi.testertest.model.entity.Gist;
import ua.kpi.testertest.model.entity.Type;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ua.kpi.testertest.model.GistTestBuilder.aGist;
import static ua.kpi.testertest.model.GistTestBuilder.aGistDto;

class GistModelMapperTest {
    private final GistModelMapper mapper = new GistModelMapper();

    //задемити порівняння по полях
    //задемити ігнор поля, яке енрічиться
    @Test
    void testToDto() {
        //assemble
        Gist source = aGist();

        //act
        GistDTO actual = mapper.toDto(source);

        //assert
        assertThat(actual).usingRecursiveComparison().isEqualTo(aGistDto());
    }

    @Test
    void testFromDto() {
        //assemble
        GistDTO source = aGistDto();

        //act
        Gist actual = mapper.fromDto(source);

        //assert
        assertThat(actual).usingRecursiveComparison().isEqualTo(aGist());
    }



}