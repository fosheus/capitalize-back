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

    public static Post map(PostDto postDto,String fileLocation) {
        if (postDto == null) return null;
        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setText(postDto.getText());
        post.setFiles(FileMapper.map(postDto.getFiles(),postDto.getId(),fileLocation));
        post.setTags(TagMapper.map(postDto.getTags()));
        return post;
    }

    public static Post map(PostForm postForm, String fileLocation) {
        if (postForm == null) return null;
        Post post = new Post();
        post.setId(postForm.getId());
        post.setTitle(postForm.getTitle());
        post.setText(postForm.getText());
        post.setFiles(FileMapper.map(postForm.getFiles(),postForm.getId(),fileLocation));
        post.setTags(TagMapper.map(postForm.getTags()));
        return post;
    }

    public static PostDto map(Post post) throws IOException {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setText(post.getText());
        dto.setValidated(post.getValidationDate() != null);
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        dto.setValidationDate(post.getValidationDate());
        dto.setFiles(FileMapper.map(post.getFiles()));
        return dto;
    }

    public static List<PostDto> map(List<Post> posts) throws IOException {
        List<PostDto> dtos = new ArrayList<>();
        if (CollectionUtils.isEmpty(posts)) return dtos;
        for( Post post : posts) {
            dtos.add(PostMapper.map(post));
        }
        return dtos;
    }

}
