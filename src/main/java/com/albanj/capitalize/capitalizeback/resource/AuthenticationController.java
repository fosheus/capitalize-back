package com.albanj.capitalize.capitalizeback.resource;

import com.albanj.capitalize.capitalizeback.dto.UserDto;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeBadRequestException;
import com.albanj.capitalize.capitalizeback.form.UserSignupForm;
import com.albanj.capitalize.capitalizeback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserDto signUp(@Valid @RequestBody UserSignupForm userSignupForm, BindingResult bindingResult)
            throws CapitalizeBadRequestException {
        log.info("signup userSignupForm=[{}]", userSignupForm.toString());
        if (bindingResult.hasErrors()) {
            StringBuilder sb = this.manageErrors(bindingResult.getAllErrors());
            throw new CapitalizeBadRequestException(0, sb.toString(), "Le modèle envoyé n'est pas valide");
        }
        return userService.create(userSignupForm);
    }

    @GetMapping("/me")
    public UserDto me(Authentication authentication) {
        return userService.getOneByEmailOrUsername(null, authentication.getName());
    }

    private StringBuilder manageErrors(List<ObjectError> errors) {

        StringBuilder sb = new StringBuilder();
        for (ObjectError error : errors) {
            sb.append(error.getDefaultMessage());
            sb.append("\n");
        }

        return sb;
    }
}
