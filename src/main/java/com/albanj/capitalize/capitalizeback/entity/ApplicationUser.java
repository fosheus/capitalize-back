package com.albanj.capitalize.capitalizeback.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class ApplicationUser extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    @Email
    @NotNull
    private String email;

    @Column(unique = true, nullable = false)
    @NotNull
    private String username;

    @NotNull
    private String password;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    @NotNull
    private RefProfile profile;
}