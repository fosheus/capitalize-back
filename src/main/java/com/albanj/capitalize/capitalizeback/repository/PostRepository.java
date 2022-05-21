package com.albanj.capitalize.capitalizeback.repository;

import java.util.List;

import com.albanj.capitalize.capitalizeback.entity.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {

    List<Post> findAllByOwnerId(Integer ownerId);

    Page<Post> findByValidationDateNull(Pageable pageable);

    @Query("SELECT p FROM Post p LEFT JOIN p.tags t WHERE t.label IN (:tags)")
    Page<Post> findAllByTags(Pageable pageable, List<String> tags);

    // implemented in PostRepositoryImpl
    // Page<Post> findAllByCriteria(List<String> tags, String owner, Boolean
    // unvalidated, Pageable pageable);

    /*
     * @Query("SELECT p FROM Post p LEFT JOIN p.tags t LEFT JOIN p.owner o WHERE t.label IN (:tags) AND o.username like :username% AND validateDate IS NULL ?????? "
     * ) Page<Post> findAllByFilters(List<String> tags, String owner, Boolean
     * unvalidated, Pageable pageable);
     */

    Page<Post> findAllByCriteria(List<String> tags, String owner, Boolean validated, Pageable pageable);

}
