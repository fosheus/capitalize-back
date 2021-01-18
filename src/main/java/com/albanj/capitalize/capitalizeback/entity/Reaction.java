package com.albanj.capitalize.capitalizeback.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Reaction extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer value;




}
