package com.albanj.capitalize.capitalizeback.mapper.impl;

import com.albanj.capitalize.capitalizeback.dto.PostDto;
import com.albanj.capitalize.capitalizeback.entity.Post;
import com.albanj.capitalize.capitalizeback.mapper.PostMapper;
import org.springframework.stereotype.Component;

@Component
public class PostMapperImpl implements PostMapper {
    @Override
    public Post toEntity(PostDto post) {
        return toEntity(new Post(),post);
    }

    @Override
    public Post toEntity(Post post, PostDto postDto) {
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setText(postDto.getText());
        return post;
    }

    @Override
    public PostDto toDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setText(post.getText());
        dto.setValidated(post.getValidationDate()!=null);
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        dto.setValidationDate(post.getValidationDate());

        return dto;
    }

}
