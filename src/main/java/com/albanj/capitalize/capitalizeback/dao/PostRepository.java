package com.albanj.capitalize.capitalizeback.dao;

import com.albanj.capitalize.capitalizeback.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

    @Query("SELECT p FROM Post p LEFT JOIN p.owner o WHERE o.id=:ownerId ")
    List<Post> findAllByOwner(Integer ownerId);

    List<Post> findByValidationDateNull(Pageable pageable);

    @Query("SELECT p FROM Post p LEFT JOIN p.tags t WHERE t.label IN (:tags)")
    Page<Post> findAllByTags(Pageable pageable, List<String>tags);
}
