package ua.kpi.testertest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.testertest.exception.ConflicException;
import ua.kpi.testertest.model.dto.GistDTO;
import ua.kpi.testertest.model.dto.PagedResource;
import ua.kpi.testertest.service.GistService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/gists")
@Slf4j
@Profile("test")
public class GistTestController {
    private final GistService service;

    @Autowired
    public GistTestController(GistService service) {
        this.service = service;
    }

    @DeleteMapping
    public void deleteAll() {
        log.warn("Deleting all gists");
        service.deleteAllGists();
    }
}
