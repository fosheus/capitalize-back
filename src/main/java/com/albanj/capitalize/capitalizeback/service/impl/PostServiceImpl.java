package com.albanj.capitalize.capitalizeback.service.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.albanj.capitalize.capitalizeback.dto.PostDto;
import com.albanj.capitalize.capitalizeback.dto.UserDto;
import com.albanj.capitalize.capitalizeback.entity.File;
import com.albanj.capitalize.capitalizeback.entity.Post;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeBadRequestException;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeNotFoundException;
import com.albanj.capitalize.capitalizeback.form.PostForm;
import com.albanj.capitalize.capitalizeback.mapper.PostMapper;
import com.albanj.capitalize.capitalizeback.mapper.UserMapper;
import com.albanj.capitalize.capitalizeback.repository.FileRepository;
import com.albanj.capitalize.capitalizeback.repository.PostRepository;
import com.albanj.capitalize.capitalizeback.repository.TagTypeRepository;
import com.albanj.capitalize.capitalizeback.service.PostService;
import com.albanj.capitalize.capitalizeback.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final TagTypeRepository tagTypeRepository;
    private final FileRepository fileRepository;
    private final Environment env;
    private final UserService userService;

    private final String FILE_PATH;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, TagTypeRepository tagTypeRepository,
            FileRepository fileRepository, Environment env, UserService userService) {
        this.postRepository = postRepository;
        this.tagTypeRepository = tagTypeRepository;
        this.fileRepository = fileRepository;
        this.env = env;
        this.userService = userService;
        FILE_PATH = env.getProperty("capitalize.files.path");

    }

    @Override
    public List<PostDto> getAll(Integer page, Integer size, List<String> tags) throws IOException {
        Page<Post> posts = null;
        if (CollectionUtils.isEmpty(tags)) {
            posts = postRepository.findAll(getPageable(page, size, "updatedAt", false));
        } else {
            posts = postRepository.findAllByTags(getPageable(page, size, "updatedAt", false), tags);
        }
        return PostMapper.map(posts.getContent());
    }

    @Override
    public PostDto getOne(Integer id) throws IOException {

        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new CapitalizeNotFoundException();
        }
        Post p = post.get();
        return PostMapper.map(p);
    }

    @Override
    public List<PostDto> getAllUnvalidated(Integer page, Integer size) throws IOException {
        List<Post> posts = postRepository.findByValidationDateNull(getPageable(page, size, "updatedAt", false));
        return PostMapper.map(posts);
    }

    @Override
    public PostDto create(UserDto user, PostForm postForm) throws Exception {

        if (postForm.getId() != null) {
            throw new Exception();
        }
        Post post = PostMapper.map(postForm, FILE_PATH);
        post.setOwner(UserMapper.map(user));
        post = postRepository.save(post);
        for (File f : post.getFiles()) {
            f.setFullPath(Paths.get(FILE_PATH, String.valueOf(post.getId()), f.getPath()).toString());
        }
        fileRepository.saveAll(post.getFiles());
        return PostMapper.map(postRepository.save(post));
    }

    @Override
    public PostDto update(Integer id, PostForm postForm) {
        if (postForm == null || id == null || !id.equals(postForm.getId())) {
            throw new CapitalizeBadRequestException();
        }
        Optional<Post> optPost = postRepository.findById(id);
        Post post = null;
        if (optPost.isEmpty()) {
            throw new CapitalizeNotFoundException();
        }
        post = optPost.get();

        post = PostMapper.map(postForm, post, FILE_PATH);
        post.setUpdatedAt(LocalDateTime.now());
        return PostMapper.map(postRepository.save(post));

    }

    @Override
    public PostDto validate(UserDto userDto, Integer id) {

        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            throw new CapitalizeNotFoundException();
        }
        Post post = optionalPost.get();
        post.setValidationDate(LocalDateTime.now());
        post.setValidator(UserMapper.map(userDto));
        return PostMapper.map(postRepository.save(post));
    }

    private Pageable getPageable(Integer page, Integer size, String sort, boolean ascending) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        Pageable pageable = null;
        Sort sortBy = null;
        if (sort != null && !sort.equals("")) {
            sortBy = Sort.by(sort);
            if (!ascending) {
                sortBy.descending();
            }
            pageable = PageRequest.of(page, size, sortBy);
        } else {
            pageable = PageRequest.of(page, size);
        }
        return pageable;
    }

}
