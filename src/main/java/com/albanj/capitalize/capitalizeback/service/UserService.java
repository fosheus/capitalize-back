package com.albanj.capitalize.capitalizeback.service;

import com.albanj.capitalize.capitalizeback.dto.UserDto;
import com.albanj.capitalize.capitalizeback.form.UserSignupForm;

public interface UserService {

    UserDto create(UserSignupForm userSignupForm);

    UserDto getOne(Integer id);

    void delete(Integer id);

    UserDto getOneByEmailOrUsername(String email, String username);
}
