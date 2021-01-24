package com.albanj.capitalize.capitalizeback.mapper;

import com.albanj.capitalize.capitalizeback.dto.ProfileDto;
import com.albanj.capitalize.capitalizeback.entity.RefProfile;

public class ProfileMapper {

    public static ProfileDto map(RefProfile profile) {
        if (profile ==null) return null;
        ProfileDto dto = new ProfileDto();
        dto.setId(profile.getId());
        dto.setLabel(profile.getLabel());
        return dto;
    }

    public static RefProfile map(ProfileDto dto) {
        if (dto ==null) return null;
        RefProfile entity = new RefProfile();
        entity.setId(dto.getId());
        return entity;
    }
}
