package com.albanj.capitalize.capitalizeback.service.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.albanj.capitalize.capitalizeback.dto.FileDto;
import com.albanj.capitalize.capitalizeback.dto.PostDto;
import com.albanj.capitalize.capitalizeback.dto.UserDto;
import com.albanj.capitalize.capitalizeback.entity.File;
import com.albanj.capitalize.capitalizeback.entity.Post;
import com.albanj.capitalize.capitalizeback.enums.CapitalizeErrorEnum;
import com.albanj.capitalize.capitalizeback.enums.ProfileEnum;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeBadRequestException;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeForbiddenException;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeGenericException;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeInternalException;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeNotFoundException;
import com.albanj.capitalize.capitalizeback.form.PostForm;
import com.albanj.capitalize.capitalizeback.mapper.PostMapper;
import com.albanj.capitalize.capitalizeback.mapper.UserMapper;
import com.albanj.capitalize.capitalizeback.repository.FileRepository;
import com.albanj.capitalize.capitalizeback.repository.PostRepository;
import com.albanj.capitalize.capitalizeback.service.FileService;
import com.albanj.capitalize.capitalizeback.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public PostDto getOne(Integer id) throws IOException, CapitalizeNotFoundException {

        Post post = repo.findById(id)
                .orElseThrow(() -> new CapitalizeNotFoundException(CapitalizeErrorEnum.POST_NOT_FOUND.code,
                        MessageFormat.format(CapitalizeErrorEnum.POST_NOT_FOUND.text, id),
                        MessageFormat.format("PostServiceImpl::getOne Post id={0} does not exist in database", id)));
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
            throw new CapitalizeBadRequestException(CapitalizeErrorEnum.INVALID_ARGUMENT.code,
                    MessageFormat.format(CapitalizeErrorEnum.INVALID_ARGUMENT.text, "postForm.getId", "!=null"),
                    "PostServiceImpl::create PostForm id must be null");
        }
        Post post = PostMapper.map(postForm, this.fileService.getFileSavePath());
        post.setOwner(UserMapper.map(user));
        return PostMapper.map(repo.save(post));
    }

    @Override
    public PostDto update(UserDto userDto, Integer id, PostForm postForm) throws CapitalizeGenericException {
        if (postForm == null || id == null || !id.equals(postForm.getId())) {
            throw new CapitalizeBadRequestException(CapitalizeErrorEnum.INVALID_ARGUMENT.code,
                    MessageFormat.format(CapitalizeErrorEnum.INVALID_ARGUMENT.text, "postForm|id", "null"),
                    MessageFormat.format(
                            "PostServiceImpl::update postForm is null or id is null or id != postForm.Id | postForm=[{0}];id=[{1}]",
                            postForm.toString(), id));
        }
        Post post = repo.findById(id)
                .orElseThrow(() -> new CapitalizeNotFoundException(CapitalizeErrorEnum.POST_NOT_FOUND.code,
                        MessageFormat.format(CapitalizeErrorEnum.POST_NOT_FOUND.text, id)));

        this.checkUserHasRightOrThrowForbidden(userDto, post,
                Arrays.asList(ProfileEnum.ADMIN.name(), ProfileEnum.ARCHITECT.name()));

        post = PostMapper.map(postForm, post, this.fileService.getFileSavePath());
        post.setUpdatedAt(LocalDateTime.now());
        return PostMapper.map(repo.save(post));

    }

    @Override
    public PostDto validate(UserDto userDto, Integer id) throws CapitalizeNotFoundException {

        Post post = repo.findById(id)
                .orElseThrow(() -> new CapitalizeNotFoundException(CapitalizeErrorEnum.POST_NOT_FOUND.code,
                        MessageFormat.format(CapitalizeErrorEnum.POST_NOT_FOUND.text, id),
                        MessageFormat.format("PostServiceImpl::validate Post id={0} does not exist in database", id)));
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
    public void deleteFile(UserDto userDto, Integer postId, Integer fileId) throws CapitalizeGenericException {

        File file = fileRepository.findById(fileId).orElseThrow(() -> new CapitalizeNotFoundException(
                CapitalizeErrorEnum.FILE_NOT_FOUND.code,
                MessageFormat.format(CapitalizeErrorEnum.FILE_NOT_FOUND.text, fileId),
                MessageFormat.format("PostServiceImpl::deleteFile File id={0} does not exist in database", fileId)));

        if (file.getPost() == null || file.getPost().getId() != postId) {
            throw new CapitalizeInternalException(MessageFormat.format(
                    "PostServiceImpl::deleteFile : file post is null or file.getPost.getId != from postId argument (file.getPost()==null)={0} - (file.getPost().getId() != postId)={1}",
                    file.getPost() == null, file.getPost().getId() != postId));
        }
        checkUserHasRightOrThrowForbidden(userDto, file.getPost(),
                Arrays.asList(ProfileEnum.ADMIN.name(), ProfileEnum.ARCHITECT.name()));

        this.fileService.deleteFile(file);
    }

    @Override
    public FileDto createFile(UserDto userDto, Integer postId, FileDto fileDto, MultipartFile file, String text)
            throws CapitalizeNotFoundException, CapitalizeForbiddenException, CapitalizeInternalException,
            CapitalizeBadRequestException {

        Post post = repo.findById(postId)
                .orElseThrow(() -> new CapitalizeNotFoundException(CapitalizeErrorEnum.POST_NOT_FOUND.code,
                        MessageFormat.format(CapitalizeErrorEnum.POST_NOT_FOUND.text, postId)));

        this.checkUserHasRightOrThrowForbidden(userDto, post,
                Arrays.asList(ProfileEnum.ADMIN.name(), ProfileEnum.ARCHITECT.name()));
        return this.fileService.updateFileContent(text, file, fileDto, post);

    }

    @Override
    public FileDto updateFile(UserDto userDto, Integer postId, Integer fileId, FileDto fileDto, MultipartFile file,
            String text) throws CapitalizeGenericException {
        Post post = repo.findById(postId).orElseThrow(() -> new CapitalizeNotFoundException(
                CapitalizeErrorEnum.POST_NOT_FOUND.code,
                MessageFormat.format(CapitalizeErrorEnum.POST_NOT_FOUND.text, postId),
                MessageFormat.format("PostServiceImpl::updateFile Post id={0} does not exist in database", postId)));

        this.checkUserHasRightOrThrowForbidden(userDto, post,
                Arrays.asList(ProfileEnum.ADMIN.name(), ProfileEnum.ARCHITECT.name()));

        if (!fileDto.getId().equals(fileId)) {
            throw new CapitalizeBadRequestException(CapitalizeErrorEnum.INVALID_ARGUMENT.code,
                    MessageFormat.format(CapitalizeErrorEnum.INVALID_ARGUMENT.text, "fileDto.getId()", "!=" + fileId),
                    MessageFormat.format(
                            "PostServiceImpl::updateFile fileDto.id must be equal to fileId | fileDto.id={0};fileId={1}",
                            fileDto.getId(), fileId));
        }

        return this.fileService.updateFileContent(text, file, fileDto, post);

    }

    @Override
    public void checkUserHasRightOrThrowForbidden(UserDto user, Post post, List<String> overrides)
            throws CapitalizeForbiddenException {

        if (overrides.contains(user.getProfile().getLabel())) {
            return;
        }
        if (!post.getOwner().getId().equals(user.getId())) {
            throw new CapitalizeForbiddenException(CapitalizeErrorEnum.FORBIDDEN.code,
                    CapitalizeErrorEnum.FORBIDDEN.text,
                    "User is not owner of the post nor has the right profile : [" + String.join(",", overrides) + "]");
        }
    }

}
