package com.albanj.capitalize.capitalizeback.repository;

import java.util.List;

import com.albanj.capitalize.capitalizeback.entity.Tag;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag, Integer> {

	List<Tag> findDistinctByLabelStartsWithIgnoreCaseOrderByLabel(String label, Pageable limit);

	@Query("SELECT DISTINCT t.label FROM Tag t WHERE t.label like :label%")
	List<String> findTagLabels(String label, Pageable limit);

}
