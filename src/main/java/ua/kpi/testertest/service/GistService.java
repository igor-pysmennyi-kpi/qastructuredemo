package ua.kpi.testertest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.kpi.testertest.exception.ResourceNotFoundException;
import ua.kpi.testertest.model.GistModelMapper;
import ua.kpi.testertest.model.PageMapper;
import ua.kpi.testertest.model.dto.GistDTO;
import ua.kpi.testertest.model.dto.PagedResource;
import ua.kpi.testertest.model.entity.Gist;
import ua.kpi.testertest.model.repository.GistRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GistService {
    private final GistModelMapper modelMapper;
    private final PageMapper pageMapper;
    private final GistRepository repository;
    private final List<GistDtoEnricher>enrichers;

    @Autowired
    public GistService(GistModelMapper modelMapper, PageMapper pageMapper, GistRepository repository, List<GistDtoEnricher> enrichers) {
        this.modelMapper = modelMapper;
        this.pageMapper = pageMapper;
        this.repository = repository;
        this.enrichers = enrichers;
    }

    public GistDTO createGist(GistDTO source) {
        Gist gist = modelMapper.fromDto(source);
        gist = repository.save(gist);
        return modelMapper.toDto(gist);
    }
   
    public GistDTO updateGist(GistDTO source) {
        //todo: who needs these checks?! what coward added this?!
//        if (!repository.existsById(source.getUuid()))
//            throw new EntityNotFoundException("Could not find gist with id " + source.getUuid());
        Gist gist = modelMapper.fromDto(source);
        gist = repository.save(gist);
        return modelMapper.toDto(gist);
    }

    public void deleteGist(UUID id) {
        repository.deleteById(id);
    }

    public GistDTO getGist(UUID id) {
        return repository
                .findById(id)
                .map(modelMapper::toDto)
                .map(this::enrich)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find gist with id " + id));
    }

    public List<GistDTO> getAll() {
        return repository
                .findAll().stream()
                .map(modelMapper::toDto)
                .map(this::enrich)
                .toList();
    }

    public PagedResource<GistDTO> getAllValidUntil(LocalDateTime doom, Pageable pageable) {
        Objects.requireNonNull(doom);
        return pageMapper.toPagedResource(repository
                .findAllByValidUntilLessThanOrderByValidUntil(doom, pageable)
                .map(modelMapper::toDto));
    }

    /**
     * Example of ugly method with ugly tests
     * Processes a Gist: validates input, checks existence, checks expiration,
     * and updates the description if valid and not expired.
     *
     * @param gistToProcess DTO containing the ID and potentially new description.
     * @return The updated GistDTO if processing was successful.
     * @throws IllegalArgumentException if input DTO or its ID is null.
     * @throws ResourceNotFoundException if the Gist with the given ID doesn't exist.
     * @throws IllegalStateException if the Gist has already expired.
     */
    public GistDTO processAndValidateGist(GistDTO gistToProcess) {
        log.info("Attempting to process and validate Gist: {}", gistToProcess);

        // Reason 1: Input Validation Failure
        if (gistToProcess == null || gistToProcess.getId() == null) {
            log.error("Input GistDTO or its UUID is null.");
            throw new IllegalArgumentException("Input Gist or its ID cannot be null.");
        }

        UUID id = gistToProcess.getId();

        // Reason 2: Existence Check Failure
        Gist existingGist = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Gist not found for processing with id: {}", id);
                    return new ResourceNotFoundException("Gist with id " + id + " not found for processing.");
                });

        // Reason 3: Business Rule (Expiration) Failure
        if (existingGist.getValidUntil() != null && LocalDateTime.now().isAfter(existingGist.getValidUntil())) {
            log.warn("Gist with id {} has expired (Valid until: {})", id, existingGist.getValidUntil());
            throw new IllegalStateException("Cannot process Gist " + id + " because it has expired.");
        }

        // Core Logic: Update description (could also fail, e.g., DB constraints)
        log.debug("Gist {} is valid for processing. Updating description.", id);
        existingGist.setContent(gistToProcess.getContent()); // Update from input DTO
        Gist updatedGist = repository.save(existingGist); // Reason 4: Persistence Failure
        log.info("Successfully processed and updated Gist with id: {}", id);


        return modelMapper.toDto(updatedGist); // Reason 5: Mapping Failure (less likely but possible)
    }

    private GistDTO enrich(GistDTO gistDTO) {
       for (GistDtoEnricher enricher : enrichers) {
           gistDTO = enricher.enrich(gistDTO);
       }
       return gistDTO;
    }
}
