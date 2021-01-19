package com.albanj.capitalize.capitalizeback.dao;

import com.albanj.capitalize.capitalizeback.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File,Integer> {
}
