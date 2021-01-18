package com.albanj.capitalize.capitalizeback.mapper;

import com.albanj.capitalize.capitalizeback.dto.PostDto;
import com.albanj.capitalize.capitalizeback.entity.Post;

public interface PostMapper {

    Post toEntity(PostDto post);

    Post toEntity(Post post , PostDto postDto);

    PostDto toDto(Post post);
}
