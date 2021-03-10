package com.albanj.capitalize.capitalizeback.form;

import java.io.Serializable;

import com.albanj.capitalize.capitalizeback.validator.UserSignupForm.UserSignupFormValidation;
import lombok.Data;

@Data
@UserSignupFormValidation
public class UserSignupForm implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -2462470214205441728L;
    private String email;
    private String username;
    private String password;

}
