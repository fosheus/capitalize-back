package com.albanj.capitalize.capitalizeback.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Post extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String title;

    @Column(columnDefinition = "mediumtext", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private ApplicationUser owner;

    @ManyToOne
    @JoinColumn(name = "validator_id")
    private ApplicationUser validator;

    private LocalDateTime validationDate;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = { CascadeType.REMOVE }, orphanRemoval = true)
    private Set<File> files = new HashSet<>();
}
