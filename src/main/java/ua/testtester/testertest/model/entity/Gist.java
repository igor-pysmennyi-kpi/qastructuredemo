package ua.testtester.testertest.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "gist")
@Getter
@Setter
public class Gist {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column
    private UUID id;
    private String author;
    private Type type;
    private String content;
    private LocalDateTime validUntil;
}
