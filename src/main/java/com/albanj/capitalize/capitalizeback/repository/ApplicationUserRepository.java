package com.albanj.capitalize.capitalizeback.repository;

import java.util.List;

import com.albanj.capitalize.capitalizeback.entity.ApplicationUser;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Integer> {

    ApplicationUser findByUsernameIgnoreCase(String username);

    ApplicationUser findByEmailOrUsernameAllIgnoreCase(String email, String username);

    List<ApplicationUser> findByUsernameStartsWithIgnoreCaseOrderByUsername(String username, Pageable pageable);
}
