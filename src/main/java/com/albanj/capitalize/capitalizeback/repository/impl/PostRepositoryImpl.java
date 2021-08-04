package com.albanj.capitalize.capitalizeback.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.albanj.capitalize.capitalizeback.entity.Post;
import com.albanj.capitalize.capitalizeback.repository.PostRepository;
import com.albanj.capitalize.capitalizeback.repository.PostSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PostRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PostRepository repo;

    public Page<Post> findAllByCriteria(List<String> tags, String owner, Boolean unvalidated, Pageable pageable) {

        Specification<Post> specification = new PostSpecification(tags, owner, unvalidated);

        return this.repo.findAll(specification, pageable);
    }

}
