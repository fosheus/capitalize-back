package com.albanj.capitalize.capitalizeback.resource;

import com.albanj.capitalize.capitalizeback.dto.UserDto;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeBadRequestException;
import com.albanj.capitalize.capitalizeback.form.UserSignupForm;
import com.albanj.capitalize.capitalizeback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserDto signUp(@Valid @RequestBody UserSignupForm userSignupForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CapitalizeBadRequestException("Le modèle envoyé n'est pas valid");
        }
        return userService.create(userSignupForm);
    }

    @GetMapping("/me")
    public UserDto me(Authentication authentication) {
        return userService.getOneByEmailOrUsername(null,authentication.getName());
    }
}
