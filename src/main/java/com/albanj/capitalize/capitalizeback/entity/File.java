package com.albanj.capitalize.capitalizeback.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class File extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String path;
    private String fullPath;
    private String type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;
}
