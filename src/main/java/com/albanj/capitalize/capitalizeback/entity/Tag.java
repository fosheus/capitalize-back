package com.albanj.capitalize.capitalizeback.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Tag extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "varchar(50)",nullable = false)
    private String label;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ref_tag_type_id")
    private RefTagType type;
}