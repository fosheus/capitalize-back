package com.albanj.capitalize.capitalizeback.form;

import com.albanj.capitalize.capitalizeback.dto.ProfileDto;
import com.albanj.capitalize.capitalizeback.validator.Profile.ProfileValidation;

import lombok.Data;

@Data
public class UserUpdateForm {

    private Integer id;
    private String email;
    private String username;
    private String password;
    @ProfileValidation
    private ProfileDto profile;

}
