package com.albanj.capitalize.capitalizeback.service;

import com.albanj.capitalize.capitalizeback.dto.ProfileDto;

public interface ProfileService {

    ProfileDto getOneById(Integer id);
    ProfileDto getOneByLabel(String label);
}
