package ua.kpi.testertest.service;

import org.springframework.stereotype.Service;
import ua.kpi.testertest.model.dto.GistDTO;

import java.time.LocalDateTime;

@Service
public class GistDtoRetrievedTimeEnricher implements GistDtoEnricher {
    @Override
    public GistDTO enrich(GistDTO gistDto) {
        LocalDateTime currentTime = LocalDateTime.now();
        return gistDto.toBuilder()
                .retrievedTime(currentTime)
                .build();//як це протестити. розкажи про ІоС
    }
}
