package com.albanj.capitalize.capitalizeback.service.impl;

import com.albanj.capitalize.capitalizeback.dto.UserDto;
import com.albanj.capitalize.capitalizeback.entity.ApplicationUser;
import com.albanj.capitalize.capitalizeback.entity.RefProfile;
import com.albanj.capitalize.capitalizeback.enums.ProfileEnum;
import com.albanj.capitalize.capitalizeback.exception.NotFoundException;
import com.albanj.capitalize.capitalizeback.form.UserSignupForm;
import com.albanj.capitalize.capitalizeback.mapper.UserMapper;
import com.albanj.capitalize.capitalizeback.repository.ApplicationUserRepository;
import com.albanj.capitalize.capitalizeback.repository.ProfileRepository;
import com.albanj.capitalize.capitalizeback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final ApplicationUserRepository repo;
    private final UserMapper mapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ProfileRepository profileRepository;


    @Autowired
    public UserServiceImpl(ApplicationUserRepository repo, UserMapper mapper, BCryptPasswordEncoder bCryptPasswordEncoder, ProfileRepository profileRepository) {
        this.repo = repo;
        this.mapper = mapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.profileRepository = profileRepository;
    }

    @Override
    public UserDto create(UserSignupForm userSignupForm) {

        ApplicationUser user = mapper.map(userSignupForm);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        RefProfile profile = profileRepository.findOneByLabel(ProfileEnum.USER.name());
        user.setProfile(profile);
        return mapper.map(repo.save(user));
    }

    @Override
    public UserDto getOne(Integer id) {
        Optional<ApplicationUser> user = repo.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException();
        }
        return mapper.map(user.get());
    }

    @Override
    public void delete(Integer id) {
        Optional<ApplicationUser> user = repo.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException();
        }
        repo.deleteById(id);
    }

    @Override
    public UserDto getOneByEmailOrUsername(String email, String username) {
        ApplicationUser user = repo.findByEmailOrUsername(email,username);
        return mapper.map(user);
    }
}
