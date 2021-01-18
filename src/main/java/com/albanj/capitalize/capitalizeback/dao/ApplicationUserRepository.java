package com.albanj.capitalize.capitalizeback.dao;


import com.albanj.capitalize.capitalizeback.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser,Integer> {

    ApplicationUser findByUsername(String username);
}
