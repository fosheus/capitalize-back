package com.albanj.capitalize.capitalizeback.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.albanj.capitalize.capitalizeback.entity.ApplicationUser;
import com.albanj.capitalize.capitalizeback.entity.Post;
import com.albanj.capitalize.capitalizeback.entity.Tag;
import com.albanj.capitalize.capitalizeback.repository.PostRepository;
import com.albanj.capitalize.capitalizeback.repository.PostSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class PostRepositoryImpl {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private PostRepository repo;

	/*
	 * public Page<Post> findAllByCriteria(List<String> tags, String owner, Boolean
	 * unvalidated, Pageable pageable) {
	 * 
	 * StringBuilder sb = new StringBuilder(); int pageNumber =
	 * pageable.getPageNumber(); int pageSize = pageable.getPageSize(); boolean
	 * useTags = !CollectionUtils.isEmpty(tags); boolean useUsername = owner != null
	 * && !owner.equals(""); boolean useUnvalidated = unvalidated != null;
	 * 
	 * sb.append(" FROM Post p LEFT JOIN p.tags t LEFT JOIN p.owner o "); if
	 * (useTags || useUsername || useUnvalidated) { sb.append("WHERE "); if
	 * (useTags) { sb.append("t.label IN (:tags) AND "); } if (useUsername) {
	 * sb.append("LOWER(o.username) = LOWER(:owner) AND "); } if (useUnvalidated) {
	 * sb.append(unvalidated.equals(Boolean.TRUE) ?
	 * "p.validationDate IS NOT NULL AND " : "p.validationDate IS NULL AND "); }
	 * sb.delete(sb.length() - 5, sb.length() - 1); }
	 * sb.append("ORDER BY p.updatedAt DESC");
	 * 
	 * TypedQuery<Post> query = entityManager.createQuery("SELECT DISTINCT p " +
	 * sb.toString(), Post.class);
	 * 
	 * query.setMaxResults(pageSize); query.setFirstResult(pageNumber * pageSize);
	 * 
	 * Query queryTotal = entityManager.createQuery("Select count(DISTINCT p.id) " +
	 * sb.toString());
	 * 
	 * if (useTags) { query.setParameter("tags", tags);
	 * queryTotal.setParameter("tags", tags); } if (useUsername) {
	 * query.setParameter("owner", owner); queryTotal.setParameter("owner", owner);
	 * }
	 * 
	 * long countResult = (long) queryTotal.getSingleResult(); int i = (int)
	 * countResult;
	 * 
	 * return new PageImpl<>(query.getResultList(), pageable, i);
	 * 
	 * }
	 */

	public Page<Post> findAllByCriteria(List<String> tags, String owner, Boolean unvalidated, Pageable pageable) {

		Specification<Post> specification = new PostSpecification(tags, owner, unvalidated);

		return this.repo.findAll(specification, pageable);
	}

}
