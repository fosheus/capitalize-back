package com.albanj.capitalize.capitalizeback.service;

import com.albanj.capitalize.capitalizeback.dto.PostDto;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface PostService {

    PostDto getOne(Integer id) throws IOException;

    List<PostDto> getAllUnvalidated(Integer page, Integer size);

    List<PostDto> getAll(Integer page, Integer size,List<String> tags);

    PostDto create(PostDto post) throws Exception;

}
