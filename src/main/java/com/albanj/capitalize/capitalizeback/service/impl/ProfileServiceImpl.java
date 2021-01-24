package com.albanj.capitalize.capitalizeback.service.impl;

import com.albanj.capitalize.capitalizeback.dto.ProfileDto;
import com.albanj.capitalize.capitalizeback.repository.ProfileRepository;
import com.albanj.capitalize.capitalizeback.service.ProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository, ModelMapper modelMapper) {
        this.profileRepository = profileRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProfileDto getOneById(Integer id) {
        return modelMapper.map(profileRepository.getOne(id),ProfileDto.class);
    }

    @Override
    public ProfileDto getOneByLabel(String label) {
        return modelMapper.map(profileRepository.findOneByLabel(label),ProfileDto.class);
    }
}
