package com.albanj.capitalize.capitalizeback.mapper;

import com.albanj.capitalize.capitalizeback.dto.UserDto;
import com.albanj.capitalize.capitalizeback.entity.ApplicationUser;
import com.albanj.capitalize.capitalizeback.form.UserSignupForm;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    public ApplicationUser map(UserDto userDto) {
        return map(new ApplicationUser(),userDto);
    }

    public ApplicationUser map(ApplicationUser user, UserDto userDto) {
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        return user;
    }

    public UserDto map(ApplicationUser user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        if (user.getProfile() != null) {
            dto.setProfile(user.getProfile().getLabel());
        }
        return dto;
    }

    public ApplicationUser map(UserSignupForm userSignupForm) {
        ApplicationUser user = new ApplicationUser();
        user.setUsername(userSignupForm.getUsername());
        user.setEmail(userSignupForm.getEmail());
        user.setPassword(userSignupForm.getPassword());
        return user;
    }
}
