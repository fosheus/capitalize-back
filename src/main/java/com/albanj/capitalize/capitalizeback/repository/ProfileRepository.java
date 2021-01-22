package com.albanj.capitalize.capitalizeback.repository;

import com.albanj.capitalize.capitalizeback.entity.RefProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<RefProfile,Integer> {

    RefProfile findOneByLabel(String label);
}
