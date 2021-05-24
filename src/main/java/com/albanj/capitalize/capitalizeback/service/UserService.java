package com.albanj.capitalize.capitalizeback.service;

import java.util.List;

import com.albanj.capitalize.capitalizeback.dto.UserDto;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeNotFoundException;
import com.albanj.capitalize.capitalizeback.form.UserSignupForm;

public interface UserService {

    List<UserDto> getAll(String username, Integer limit);

    UserDto create(UserSignupForm userSignupForm);

    UserDto getOne(Integer id) throws CapitalizeNotFoundException;

    void delete(Integer id) throws CapitalizeNotFoundException;

    UserDto getOneByEmailOrUsername(String email, String username);
}
