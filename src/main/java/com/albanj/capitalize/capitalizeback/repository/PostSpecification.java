package com.albanj.capitalize.capitalizeback.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.albanj.capitalize.capitalizeback.entity.ApplicationUser;
import com.albanj.capitalize.capitalizeback.entity.Post;
import com.albanj.capitalize.capitalizeback.entity.Tag;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostSpecification implements Specification<Post> {

    private List<String> tags;
    private String owner;
    private Boolean validated;

    @Override
    public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Join<Post, Tag> joinTags = root.join("tags");
        Join<Post, ApplicationUser> joinOwner = root.join("owner");

        List<Predicate> predicates = new ArrayList<>();

        if (!CollectionUtils.isEmpty(tags)) {
            predicates.add(builder.in(joinTags.get("label")).value(tags));
        }
        if (owner != null && !owner.equals("")) {
            predicates.add(builder.like(joinOwner.get("username"), owner));
        }
        if (validated != null) {
            if (validated.equals(Boolean.FALSE)) {
                predicates.add(builder.isNull(root.get("validationDate")));
            } else {
                predicates.add(builder.isNotNull(root.get("validationDate")));
            }
        }

        query.distinct(true);
        return builder.and(predicates.toArray(new Predicate[0]));
    }

}
