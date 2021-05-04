package com.albanj.capitalize.capitalizeback.resource;

import com.albanj.capitalize.capitalizeback.dto.UserDto;
import com.albanj.capitalize.capitalizeback.enums.CapitalizeErrorEnum;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeBadRequestException;
import com.albanj.capitalize.capitalizeback.form.UserSignupForm;
import com.albanj.capitalize.capitalizeback.log.LogMessageBuilder;
import com.albanj.capitalize.capitalizeback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

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
            throw new CapitalizeBadRequestException(CapitalizeErrorEnum.SIGNUP_FORM_INVALID.code,
                    CapitalizeErrorEnum.SIGNUP_FORM_INVALID.text, "Le modèle envoyé n'est pas valid");
        }
        return userService.create(userSignupForm);
    }

    @GetMapping("/me")
    public UserDto me(Authentication authentication) {
        log.info(LogMessageBuilder.buildHeader(authentication) + " me");
        return userService.getOneByEmailOrUsername(null, authentication.getName());
    }
}
