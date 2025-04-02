package ua.kpi.testertest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.kpi.testertest.model.GistModelMapper;
import ua.kpi.testertest.model.PageMapper;
import ua.kpi.testertest.model.dto.GistDTO;
import ua.kpi.testertest.model.repository.GistRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static ua.kpi.testertest.model.GistTestBuilder.aGist;
import static ua.kpi.testertest.model.GistTestBuilder.aGistDto;

@ExtendWith(MockitoExtension.class)
class GistServiceTest {
    @Mock
    private GistModelMapper modelMapper;
    @Mock
    private PageMapper pageMapper;
    @Mock
    private GistRepository repository;
    @Mock
    GistDtoEnricher enricher;

    private GistService gistService;
    @BeforeEach
    void setUp() {
        gistService = new GistService(modelMapper, pageMapper, repository, List.of(enricher));
    }

    @Test
    void testGetAllEmpty() {
        when(repository.findAll()).thenReturn(List.of());

        var actual = gistService.getAll();

        assertThat(actual).isEmpty();
        verify(repository, times(1)).findAll();
        verifyNoInteractions(modelMapper, pageMapper);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void testGetAll() {

        var gist = aGist();
        when(repository.findAll()).thenReturn(List.of(gist));
        when(modelMapper.toDto(any())).thenReturn(anOldGist());
        when(enricher.enrich(anOldGist())).thenReturn(aGistDto());

        var actual = gistService.getAll();

        assertThat(actual).containsExactly(aGistDto());

        verify(repository, times(1)).findAll();
        verify(modelMapper, times(1)).toDto(gist);
        verify(enricher, times(1)).enrich(anOldGist());
    }

    GistDTO anOldGist(){
        return aGistDto().toBuilder()
                .validFrom(LocalDateTime.of(2026, 12, 30, 23, 59, 59))
                .validUntil(LocalDateTime.of(2026, 12, 31, 23, 59, 59))
                .build();
    }
}