package com.albanj.capitalize.capitalizeback.resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWorld {

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/world")
    public String signUp(Authentication authentication) {
        return authentication.getName();
    }

}
