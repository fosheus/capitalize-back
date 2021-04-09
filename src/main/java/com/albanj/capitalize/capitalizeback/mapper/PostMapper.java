package com.albanj.capitalize.capitalizeback.mapper;

import com.albanj.capitalize.capitalizeback.dto.PostDto;
import com.albanj.capitalize.capitalizeback.entity.Post;
import com.albanj.capitalize.capitalizeback.form.PostForm;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostMapper {

    public static Post map(PostDto postDto, String fileLocation) {
        if (postDto == null)
            return null;
        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.getFiles().addAll(FileMapper.map(postDto.getFiles(), post, fileLocation));
        post.getTags().addAll(TagMapper.map(postDto.getTags(), post));
        return post;
    }

    public static Post map(PostForm postForm, String fileLocation) {
        return PostMapper.map(postForm, new Post(), fileLocation);
    }

    public static Post map(PostForm postForm, Post post, String fileLocation) {
        if (postForm == null)
            return null;
        if (post == null) {
            post = new Post();
            post.setId(postForm.getId());
        }
        post.setTitle(postForm.getTitle());
        post.setDescription(postForm.getDescription());
        post.getFiles().clear();
        post.getTags().clear();
        post.getFiles().addAll(FileMapper.map(postForm.getFiles(), post, fileLocation));
        post.getTags().addAll(TagMapper.map(postForm.getTags(), post));
        return post;
    }

    public static PostDto map(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setValidated(post.getValidationDate() != null);
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        dto.setValidationDate(post.getValidationDate());
        dto.setFiles(FileMapper.map(post.getFiles()));
        dto.setTags(TagMapper.map(post.getTags()));
        dto.setOwner(UserMapper.map(post.getOwner()));
        dto.setValidator(UserMapper.map(post.getValidator()));
        return dto;
    }

    public static List<PostDto> map(List<Post> posts) throws IOException {
        List<PostDto> dtos = new ArrayList<>();
        if (CollectionUtils.isEmpty(posts))
            return dtos;
        for (Post post : posts) {
            dtos.add(PostMapper.map(post));
        }
        return dtos;
    }

}
