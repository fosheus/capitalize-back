package com.albanj.capitalize.capitalizeback.service;

import java.io.IOException;
import java.util.List;

import com.albanj.capitalize.capitalizeback.dto.PostDto;
import com.albanj.capitalize.capitalizeback.dto.UserDto;
import com.albanj.capitalize.capitalizeback.form.PostForm;

public interface PostService {

    PostDto getOne(Integer id) throws IOException;

    List<PostDto> getAllUnvalidated(Integer page, Integer size) throws IOException;

    List<PostDto> getAll(Integer page, Integer size, List<String> tags) throws IOException;

    PostDto create(UserDto userDto, PostForm post) throws Exception;

    PostDto update(Integer id, PostForm post);

    PostDto validate(UserDto userDto, Integer id);

}
