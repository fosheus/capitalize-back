package com.albanj.capitalize.capitalizeback.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.albanj.capitalize.capitalizeback.dto.UserDto;
import com.albanj.capitalize.capitalizeback.entity.ApplicationUser;
import com.albanj.capitalize.capitalizeback.entity.RefProfile;
import com.albanj.capitalize.capitalizeback.enums.CapitalizeErrorEnum;
import com.albanj.capitalize.capitalizeback.enums.ProfileEnum;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeNotFoundException;
import com.albanj.capitalize.capitalizeback.form.UserSignupForm;
import com.albanj.capitalize.capitalizeback.mapper.UserMapper;
import com.albanj.capitalize.capitalizeback.repository.ApplicationUserRepository;
import com.albanj.capitalize.capitalizeback.repository.ProfileRepository;
import com.albanj.capitalize.capitalizeback.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final ApplicationUserRepository repo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ProfileRepository profileRepository;

    @Autowired
    public UserServiceImpl(ApplicationUserRepository repo, BCryptPasswordEncoder bCryptPasswordEncoder,
            ProfileRepository profileRepository) {
        this.repo = repo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.profileRepository = profileRepository;
    }

    @Override
    public List<UserDto> getAll(String username, Integer limit) {

        if (username != null && username.length() > 0) {
            PageRequest limitPage = PageRequest.of(0, limit);
            return UserMapper.map(this.repo.findByUsernameStartsWithIgnoreCaseOrderByUsername(username, limitPage));
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public UserDto create(UserSignupForm userSignupForm) {

        ApplicationUser user = UserMapper.map(userSignupForm);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        RefProfile profile = profileRepository.findOneByLabel(ProfileEnum.USER.name());
        user.setProfile(profile);
        return UserMapper.map(repo.save(user));
    }

    @Override
    public UserDto getOne(Integer id) throws CapitalizeNotFoundException {
        ApplicationUser user = repo.findById(id)
                .orElseThrow(() -> new CapitalizeNotFoundException(CapitalizeErrorEnum.NOT_FOUND.code,
                        CapitalizeErrorEnum.NOT_FOUND.text,
                        MessageFormat.format("UserServiceImpl::getOne User id={0} does not exist in database", id)));
        return UserMapper.map(user);
    }

    @Override
    public void delete(Integer id) throws CapitalizeNotFoundException {
        ApplicationUser user = repo.findById(id)
                .orElseThrow(() -> new CapitalizeNotFoundException(CapitalizeErrorEnum.NOT_FOUND.code,
                        CapitalizeErrorEnum.NOT_FOUND.text,
                        MessageFormat.format("UserServiceImpl::getOne User id={0} does not exist in database", id)));
        repo.delete(user);
    }

    @Override
    public UserDto getOneByEmailOrUsername(String email, String username) {
        ApplicationUser user = repo.findByEmailOrUsernameAllIgnoreCase(email, username);
        return UserMapper.map(user);
    }

}
