package ua.kpi.testertest.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "gist")
@Getter
@Setter
public class Gist {
    @Id
    @GeneratedValue(generator = "uuid")
    @Column
    private UUID id;
    private String author;
    private Type type;
    private String content;
    private LocalDateTime validFrom;
    private LocalDateTime validUntil;

}
