package ua.kpi.testertest.service;

import ua.kpi.testertest.model.dto.GistDTO;

public interface GistDtoEnricher {
    GistDTO enrich(GistDTO gistDto);
}
