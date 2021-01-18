package com.albanj.capitalize.capitalizeback.service.impl;

import com.albanj.capitalize.capitalizeback.dao.PostRepository;
import com.albanj.capitalize.capitalizeback.dto.FileDto;
import com.albanj.capitalize.capitalizeback.dto.PostDto;
import com.albanj.capitalize.capitalizeback.dto.TagDto;
import com.albanj.capitalize.capitalizeback.entity.File;
import com.albanj.capitalize.capitalizeback.entity.Post;
import com.albanj.capitalize.capitalizeback.entity.Tag;
import com.albanj.capitalize.capitalizeback.exception.NotFoundException;
import com.albanj.capitalize.capitalizeback.mapper.PostMapper;
import com.albanj.capitalize.capitalizeback.mapper.UserMapper;
import com.albanj.capitalize.capitalizeback.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserMapper userMapper;
    private final Environment env;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper, UserMapper userMapper, Environment env) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.userMapper = userMapper;
        this.env = env;
    }

    @Override
    public List<PostDto> getAll(Integer page, Integer size,List<String> tags) {
        Page<Post> posts= null;
        if (tags.isEmpty()) {
            posts = postRepository.findAll(getPageable(page,size,"updatedAt",false));
        }else {
            posts = postRepository.findAllByTags(getPageable(page,size,"updatedAt",false),tags);
        }
        return posts.map(postMapper::toDto).toList();
    }


    @Override
    @Transactional
    public PostDto getOne(Integer id) throws IOException {

        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new NotFoundException();
        }

        Post p = post.get();
        PostDto dto = postMapper.toDto(p);
        dto.setOwner(userMapper.toDto(p.getOwner()));
        dto.setValidator(userMapper.toDto(p.getValidator()));

        for (File file : p.getFiles()) {
            String content = Files.readString(Paths.get(file.getFullPath()));
            FileDto fileDto = new FileDto();
            fileDto.setId(file.getId());
            fileDto.setPath(file.getPath());
            fileDto.setType(file.getType());
            fileDto.setContent(content);
            dto.getFiles().add(fileDto);
        }
        for (Tag tag : p.getTags()) {
            TagDto tagDto = new TagDto();
            tagDto.setId(tag.getId());
            tagDto.setLabel(tag.getLabel());
            if(tag.getType()!=null) {
                tagDto.setType(tag.getType().getLabel());
            }
        }

        return dto;
    }

    @Override
    public List<PostDto> getAllUnvalidated(Integer page, Integer size) {
        List<Post> posts= postRepository.findByValidationDateNull(getPageable(page,size,"updatedAt",false));
        return posts.stream().map(postMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public PostDto create(PostDto postDto) throws Exception {

        if (postDto.getId() != null) {
            throw new Exception();
        }
        Post post = postMapper.toEntity(postDto);
        

        return postMapper.toDto(postRepository.save(post));
    }




    private Pageable getPageable(Integer page, Integer size, String sort,boolean ascending) {
        if (page ==null) {
            page =0;
        }
        if (size==null) {
            size = 10;
        }
        Pageable pageable = null;
        Sort sortBy = null;
        if (sort != null && !sort.equals("")) {
            sortBy = Sort.by(sort);
            if (!ascending) {
                sortBy.descending();
            }
            pageable=PageRequest.of(page,size, sortBy);
        } else {
            pageable=PageRequest.of(page,size);
        }
        return pageable;
    }


}
