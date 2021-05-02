package com.albanj.capitalize.capitalizeback.service;

import java.io.IOException;
import java.util.List;

import com.albanj.capitalize.capitalizeback.dto.FileDto;
import com.albanj.capitalize.capitalizeback.dto.PostDto;
import com.albanj.capitalize.capitalizeback.dto.UserDto;
import com.albanj.capitalize.capitalizeback.entity.Post;
import com.albanj.capitalize.capitalizeback.form.PostForm;

import org.springframework.web.multipart.MultipartFile;

public interface PostService {

        PostDto getOne(Integer id) throws IOException;

        List<PostDto> getAllUnvalidated(Integer page, Integer size) throws IOException;

        List<PostDto> getAll(Integer page, Integer size, List<String> tags) throws IOException;

        PostDto create(UserDto userDto, PostForm post) throws Exception;

        PostDto update(UserDto userDto, Integer id, PostForm post);

        PostDto validate(UserDto userDto, Integer id);

        FileDto createFile(UserDto userDto, Integer postId, FileDto fileDto, MultipartFile file, String text);

        FileDto updateFile(UserDto userDto, Integer postId, Integer fileId, FileDto fileDto, MultipartFile file,
                        String text);

        void deleteFile(UserDto userDto, Integer postId, List<Integer> fileIds);

        void checkUserHasRightOrThrowForbidden(UserDto user, Post post, List<String> overrides);

}
