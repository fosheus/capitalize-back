package com.albanj.capitalize.capitalizeback.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {

    private Integer id;
    private String email;
    private String username;
    private String password;
    private ProfileDto profile;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
