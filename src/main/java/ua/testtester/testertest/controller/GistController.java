package ua.testtester.testertest.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ua.testtester.testertest.model.dto.GistDTO;
import ua.testtester.testertest.service.GistService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/gist")
@Log4j2
public class GistController {
    private final GistService service;

    @Autowired
    public GistController(GistService service) {
        this.service = service;
    }

    @PostMapping
    public GistDTO createGist(GistDTO source) {
        return service.createGist(source);
    }

    @PutMapping
    public GistDTO updateGist(GistDTO source) {
        return service.updateGist(source);
    }

    @DeleteMapping
    public void deleteGist(UUID id) {
        service.deleteGist(id);
    }

    @GetMapping("/{id}")
    public GistDTO getGist(UUID id) {
        return service.getGist(id);
    }

    @GetMapping
    public List<GistDTO> getAll() {
        return service.getAll();
    }

    //todo: consider merging it with getall() and using just one param. No time now, need to set sail and row!
    @GetMapping
    public Page<GistDTO> getAllValidUntil(LocalDateTime doom, Pageable pageable) {
        return service.getAllValidUntil(doom, pageable);
    }
}
