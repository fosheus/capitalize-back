package com.albanj.capitalize.capitalizeback.form;

import com.albanj.capitalize.capitalizeback.validator.UserSignupForm.UserSignupFormValidation;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@UserSignupFormValidation
public class UserSignupForm {
    private String email;
    private String username;
    private String password;


}
