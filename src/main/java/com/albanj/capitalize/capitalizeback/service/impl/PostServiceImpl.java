package com.albanj.capitalize.capitalizeback.service.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.albanj.capitalize.capitalizeback.dto.FileDto;
import com.albanj.capitalize.capitalizeback.dto.PostDto;
import com.albanj.capitalize.capitalizeback.dto.UserDto;
import com.albanj.capitalize.capitalizeback.entity.File;
import com.albanj.capitalize.capitalizeback.entity.Post;
import com.albanj.capitalize.capitalizeback.enums.FileTypeEnum;
import com.albanj.capitalize.capitalizeback.enums.ProfileEnum;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeBadRequestException;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeForbiddenException;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeNotFoundException;
import com.albanj.capitalize.capitalizeback.form.PostForm;
import com.albanj.capitalize.capitalizeback.mapper.FileMapper;
import com.albanj.capitalize.capitalizeback.mapper.PostMapper;
import com.albanj.capitalize.capitalizeback.mapper.UserMapper;
import com.albanj.capitalize.capitalizeback.repository.FileRepository;
import com.albanj.capitalize.capitalizeback.repository.PostRepository;
import com.albanj.capitalize.capitalizeback.repository.TagTypeRepository;
import com.albanj.capitalize.capitalizeback.service.FileService;
import com.albanj.capitalize.capitalizeback.service.PostService;
import com.albanj.capitalize.capitalizeback.service.UserService;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository repo;
    private final FileRepository fileRepository;
    private final FileService fileService;

    @Autowired
    public PostServiceImpl(PostRepository repo, FileRepository fileRepository, FileService fileService) {
        this.repo = repo;
        this.fileRepository = fileRepository;
        this.fileService = fileService;

    }

    @Override
    public List<PostDto> getAll(Integer page, Integer size, List<String> tags) throws IOException {
        Page<Post> posts = null;
        if (CollectionUtils.isEmpty(tags)) {
            posts = repo.findAll(getPageable(page, size, "updatedAt", false));
        } else {
            posts = repo.findAllByTags(getPageable(page, size, "updatedAt", false), tags);
        }
        return PostMapper.map(posts.getContent());
    }

    @Override
    public PostDto getOne(Integer id) throws IOException {

        Post post = repo.findById(id).orElseThrow(() -> new CapitalizeNotFoundException());
        return PostMapper.map(post);
    }

    @Override
    public List<PostDto> getAllUnvalidated(Integer page, Integer size) throws IOException {
        List<Post> posts = repo.findByValidationDateNull(getPageable(page, size, "updatedAt", false));
        return PostMapper.map(posts);
    }

    @Override
    public PostDto create(UserDto user, PostForm postForm) throws Exception {

        if (postForm.getId() != null) {
            throw new Exception();
        }
        Post post = PostMapper.map(postForm, this.fileService.getFileSavePath());
        post.setOwner(UserMapper.map(user));
        /*
         * post = repo.save(post); for (File f : post.getFiles()) {
         * f.setFullPath(Paths.get(FILE_PATH, String.valueOf(post.getId()),
         * f.getPath()).toString()); } fileRepository.saveAll(post.getFiles());
         */
        return PostMapper.map(repo.save(post));
    }

    @Override
    public PostDto update(UserDto userDto, Integer id, PostForm postForm) {
        if (postForm == null || id == null || !id.equals(postForm.getId())) {
            throw new CapitalizeBadRequestException();
        }
        Post post = repo.findById(id).orElseThrow(() -> new CapitalizeNotFoundException());

        this.checkUserHasRightOrThrowForbidden(userDto, post,
                Arrays.asList(ProfileEnum.ADMIN.name(), ProfileEnum.ARCHITECT.name()));

        post = PostMapper.map(postForm, post, this.fileService.getFileSavePath());
        post.setUpdatedAt(LocalDateTime.now());
        return PostMapper.map(repo.save(post));

    }

    @Override
    public PostDto validate(UserDto userDto, Integer id) {

        Post post = repo.findById(id).orElseThrow(() -> new CapitalizeNotFoundException());
        post.setValidationDate(LocalDateTime.now());
        post.setValidator(UserMapper.map(userDto));
        return PostMapper.map(repo.save(post));
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

    @Override
    public void deleteFile(UserDto userDto, Integer postId, List<Integer> fileIds) {

        final boolean isAdminOrArchitect = this.isAdminOrArchitect(userDto);

        List<File> files = fileIds.stream()
                .map(f -> this.fileRepository.findById(f).orElseThrow(() -> new CapitalizeNotFoundException()))
                .collect(Collectors.toList());

        files.stream().forEach(f -> {
            if (f.getPost() == null || f.getPost().getId() != postId) {
                throw new CapitalizeBadRequestException();
            }
            if (!f.getPost().getOwner().getUsername().equals(userDto.getUsername()) && !isAdminOrArchitect) {
                throw new CapitalizeForbiddenException();
            }
        });

        this.fileService.deleteFiles(files);
    }

    private boolean isAdminOrArchitect(UserDto user) {
        return user.getProfile().getLabel().equals(ProfileEnum.ARCHITECT.name())
                || user.getProfile().getLabel().equals(ProfileEnum.ADMIN.name());
    }

    @Override
    public FileDto createFile(UserDto userDto, Integer postId, FileDto fileDto, MultipartFile file, String text) {

        final Post post = this.repo.findById(postId).orElseThrow(() -> new CapitalizeNotFoundException());

        this.checkUserHasRightOrThrowForbidden(userDto, post,
                Arrays.asList(ProfileEnum.ADMIN.name(), ProfileEnum.ARCHITECT.name()));
        return this.fileService.updateFileContent(text, file, fileDto, post);

    }

    @Override
    public FileDto updateFile(UserDto userDto, Integer postId, Integer fileId, FileDto fileDto, MultipartFile file,
            String text) {
        final Post post = this.repo.findById(postId).orElseThrow(() -> new CapitalizeNotFoundException());

        this.checkUserHasRightOrThrowForbidden(userDto, post,
                Arrays.asList(ProfileEnum.ADMIN.name(), ProfileEnum.ARCHITECT.name()));
        if (!fileDto.getId().equals(fileId)) {
            throw new CapitalizeBadRequestException();
        }

        return this.fileService.updateFileContent(text, file, fileDto, post);

    }

    @Override
    public void checkUserHasRightOrThrowForbidden(UserDto user, Post post, List<String> overrides) {

        if (overrides.contains(user.getProfile().getLabel())) {
            return;
        }
        if (!post.getOwner().getId().equals(user.getId())) {
            throw new CapitalizeForbiddenException(
                    "User is not owner of the post nor has the right profile : [" + String.join(",", overrides) + "]");
        }
    }

}
