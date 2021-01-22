package com.albanj.capitalize.capitalizeback.dto;

import com.albanj.capitalize.capitalizeback.validator.ProfileValidation;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserDto implements Serializable {

    private Integer id;
    private String email;
    private String username;
    private String password;
    private String profile;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
