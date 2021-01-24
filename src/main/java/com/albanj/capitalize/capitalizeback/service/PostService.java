package com.albanj.capitalize.capitalizeback.service;

import com.albanj.capitalize.capitalizeback.dto.PostDto;
import com.albanj.capitalize.capitalizeback.form.PostForm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface PostService {

    PostDto getOne(Integer id) throws IOException;

    List<PostDto> getAllUnvalidated(Integer page, Integer size) throws IOException;

    List<PostDto> getAll(Integer page, Integer size,List<String> tags) throws IOException;

    PostDto create(Authentication authentication, PostForm post) throws Exception;

    PostDto update(Integer id,PostForm post);

    PostDto validate(Authentication authentication, Integer id);

}
