package com.albanj.capitalize.capitalizeback.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Data
public class RefProfile implements GrantedAuthority {

    /**
     *
     */
    private static final long serialVersionUID = 3867612184315497296L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String label;

    @Override
    public String getAuthority() {
        return label;
    }
}
