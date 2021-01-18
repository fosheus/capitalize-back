package com.albanj.capitalize.capitalizeback.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class RefTagType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "varchar(50)",nullable = false)
    private String label;
}
