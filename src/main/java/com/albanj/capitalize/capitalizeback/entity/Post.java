package com.albanj.capitalize.capitalizeback.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
public class Post extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "varchar(255)",nullable = false)
    private String title;
    @Column(columnDefinition = "mediumtext",nullable = false)
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner_id")
    private ApplicationUser owner;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="validator_id")
    private ApplicationUser validator;
    private LocalDateTime validationDate;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Tag> tags = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<File> files = new HashSet<>();
}
