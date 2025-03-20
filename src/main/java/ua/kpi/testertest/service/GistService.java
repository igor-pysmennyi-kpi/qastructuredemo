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

    @Autowired
    public GistService(GistModelMapper modelMapper, PageMapper pageMapper, GistRepository repository) {
        this.modelMapper = modelMapper;
        this.pageMapper = pageMapper;
        this.repository = repository;
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
                .orElseThrow(() -> new ResourceNotFoundException("Could not find gist with id " + id));
    }

    public List<GistDTO> getAll() {
        return repository
                .findAll().stream()
                .map(modelMapper::toDto)
                .collect(Collectors.toList());
    }

    public PagedResource<GistDTO> getAllValidUntil(LocalDateTime doom, Pageable pageable) {
        Objects.requireNonNull(doom);
        return pageMapper.toPagedResource(repository
                .findAllByValidUntilLessThanOrderByValidUntil(doom, pageable)
                .map(modelMapper::toDto));
    }
}
