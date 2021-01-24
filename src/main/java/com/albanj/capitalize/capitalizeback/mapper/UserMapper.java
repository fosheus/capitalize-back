package com.albanj.capitalize.capitalizeback.mapper;

import com.albanj.capitalize.capitalizeback.dto.UserDto;
import com.albanj.capitalize.capitalizeback.entity.ApplicationUser;
import com.albanj.capitalize.capitalizeback.form.UserSignupForm;
import com.albanj.capitalize.capitalizeback.form.UserUpdateForm;
import org.springframework.stereotype.Component;

public class UserMapper {

    public static ApplicationUser map(UserDto userDto) {
        if (userDto==null)return null;
        ApplicationUser entity = new ApplicationUser();
        entity.setId(userDto.getId());
        entity.setUsername(userDto.getUsername());
        entity.setEmail(userDto.getEmail());
        entity.setProfile(ProfileMapper.map(userDto.getProfile()));

        return entity;
    }

    public static UserDto map(ApplicationUser user) {
        if (user == null) return null;
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setProfile(ProfileMapper.map(user.getProfile()));
        return dto;
    }

    public ApplicationUser map(UserSignupForm userSignupForm) {
        if (userSignupForm == null) return null;
        ApplicationUser user = new ApplicationUser();
        user.setUsername(userSignupForm.getUsername());
        user.setEmail(userSignupForm.getEmail());
        user.setPassword(userSignupForm.getPassword());
        user.setProfile(null);
        return user;
    }

    public ApplicationUser map(UserUpdateForm form) {
        if (form == null ) return null;
        ApplicationUser user = new ApplicationUser();
        user.setUsername(form.getUsername());
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());
        user.setProfile(ProfileMapper.map(form.getProfile()));
        return user;
    }
}
