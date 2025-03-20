package ua.kpi.testertest.controller;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ua.kpi.testertest.exception.ConflicException;
import ua.kpi.testertest.model.dto.GistDTO;
import ua.kpi.testertest.model.dto.PagedResource;
import ua.kpi.testertest.service.GistService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/gist")
@Slf4j
public class GistController {
    private final GistService service;

    @Autowired
    public GistController(GistService service) {
        this.service = service;
    }

    @PostMapping
    public GistDTO createGist(@RequestBody GistDTO source) {
        return service.createGist(source);
    }

    @PutMapping("/{id}")
    public GistDTO updateGist(@PathVariable(required = false) UUID id,
                              @RequestBody GistDTO source) {
        //todo: btw, why do we need id here?! it's already in the body
        if (id != null && !source.getUuid().equals(id))
            throw new ConflicException();
        return service.updateGist(source);
    }

    @DeleteMapping("/{id}")
    public void deleteGist(@PathVariable UUID id) {
        service.deleteGist(id);
    }

    @GetMapping("/{id}")
    public GistDTO getGist(@PathVariable UUID id) {
        return service.getGist(id);
    }

    @GetMapping
    public List<GistDTO> getAll() {
        return service.getAll();
    }

    //todo: consider merging it with getall() and using just one param. No time now, need to set sail and row!
    @GetMapping("/active")
    public PagedResource<GistDTO> getAllValidUntil(
            @RequestParam("until")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                    LocalDateTime doom,
            Pageable pageable) {
        return service.getAllValidUntil(doom, pageable);
    }
}
