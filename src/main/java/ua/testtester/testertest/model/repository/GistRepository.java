package ua.testtester.testertest.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.testtester.testertest.model.entity.Gist;

import java.time.LocalDateTime;
import java.util.UUID;

public interface GistRepository extends JpaRepository<Gist, UUID> {
    Page<Gist> findAllByValidUntilLessThanOrderByValidUntil(LocalDateTime doom, Pageable page);
}
