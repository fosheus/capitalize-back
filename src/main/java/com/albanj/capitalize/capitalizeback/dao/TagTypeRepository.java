package com.albanj.capitalize.capitalizeback.dao;

import com.albanj.capitalize.capitalizeback.entity.RefTagType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagTypeRepository extends JpaRepository<RefTagType,Integer> {
}
