package ua.testtester.testertest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.testtester.testertest.exception.ResourceNotFoundException;
import ua.testtester.testertest.model.GistModelMapper;
import ua.testtester.testertest.model.PageMapper;
import ua.testtester.testertest.model.dto.GistDTO;
import ua.testtester.testertest.model.dto.PagedResource;
import ua.testtester.testertest.model.entity.Gist;
import ua.testtester.testertest.model.repository.GistRepository;
import ua.testtester.testertest.service.GistService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GistServiceImpl implements GistService {
    private final GistModelMapper modelMapper;
    private final PageMapper pageMapper;
    private final GistRepository repository;

    @Autowired
    public GistServiceImpl(GistModelMapper modelMapper, PageMapper pageMapper, GistRepository repository) {
        this.modelMapper = modelMapper;
        this.pageMapper = pageMapper;
        this.repository = repository;
    }

    @Override
    public GistDTO createGist(GistDTO source) {
        Gist gist = modelMapper.fromDto(source);
        gist = repository.save(gist);
        return modelMapper.toDto(gist);
    }

    @Override
    public GistDTO updateGist(GistDTO source) {
        //todo: who needs these checks?! what coward added this?!
//        if (!repository.existsById(source.getUuid()))
//            throw new EntityNotFoundException("Could not find gist with id " + source.getUuid());
        Gist gist = modelMapper.fromDto(source);
        gist = repository.save(gist);
        return modelMapper.toDto(gist);
    }

    @Override
    public void deleteGist(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public GistDTO getGist(UUID id) {
        return repository
                .findById(id)
                .map(modelMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find gist with id " + id));
    }

    @Override
    public List<GistDTO> getAll() {
        return repository
                .findAll().stream()
                .map(modelMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PagedResource<GistDTO> getAllValidUntil(LocalDateTime doom, Pageable pageable) {
        Objects.requireNonNull(doom);
        return pageMapper.toPagedResource(repository
                .findAllByValidUntilLessThanOrderByValidUntil(doom, pageable)
                .map(modelMapper::toDto));
    }
}
