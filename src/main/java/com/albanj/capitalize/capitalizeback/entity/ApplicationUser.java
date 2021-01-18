package com.albanj.capitalize.capitalizeback.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Entity
public class ApplicationUser extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true ,nullable = false)
    @Pattern(regexp = "/^([\\S\\d\\w]+)@((.+\\.[\\S\\d\\w]{2,})|((?:[0-9]{1,3}\\.){3}[0-9]{1,3}))$")
    private String email;
    @Column(unique = true,nullable = false)
    private String username;
    @NotNull
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="profile_id")
    @NotNull
    private RefProfile profile;
}