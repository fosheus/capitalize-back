package com.albanj.capitalize.capitalizeback.mapper.impl;

import com.albanj.capitalize.capitalizeback.dto.UserDto;
import com.albanj.capitalize.capitalizeback.entity.ApplicationUser;
import com.albanj.capitalize.capitalizeback.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {


    @Override
    public ApplicationUser toEntity(UserDto userDto) {
        return toEntity(new ApplicationUser(),userDto);
    }

    @Override
    public ApplicationUser toEntity(ApplicationUser user, UserDto userDto) {
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        return user;
    }

    @Override
    public UserDto toDto(ApplicationUser user) {
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
}
