package com.albanj.capitalize.capitalizeback.mapper;


import com.albanj.capitalize.capitalizeback.dto.UserDto;
import com.albanj.capitalize.capitalizeback.entity.ApplicationUser;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    ApplicationUser toEntity(UserDto post);

    ApplicationUser toEntity(ApplicationUser post , UserDto postDto);

    UserDto toDto(ApplicationUser post);
}
