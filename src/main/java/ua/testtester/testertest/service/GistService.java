package ua.testtester.testertest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.testtester.testertest.model.dto.GistDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface GistService {
    GistDTO createGist(GistDTO source);
    GistDTO updateGist(GistDTO source);
    void deleteGist(UUID id);
    GistDTO getGist(UUID id);
    List<GistDTO> getAll();//todo: some dander lays here, but who cares on dangers? we are bold captains of our lives!
    Page<GistDTO> getAllValidUntil(LocalDateTime doom,Pageable pageable);
}
