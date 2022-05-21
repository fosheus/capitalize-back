package com.albanj.capitalize.capitalizeback.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(exclude = "post", callSuper = false)
@Entity
@Data
public class File extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String path;
    @Column(nullable = false)
    private String fullPath;
    @Column(nullable = false)
    private String type;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

}
