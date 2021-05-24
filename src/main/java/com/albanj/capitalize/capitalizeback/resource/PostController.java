package com.albanj.capitalize.capitalizeback.resource;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import com.albanj.capitalize.capitalizeback.dto.FileDto;
import com.albanj.capitalize.capitalizeback.dto.PostDto;
import com.albanj.capitalize.capitalizeback.dto.UserDto;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeBadRequestException;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeForbiddenException;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeGenericException;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeInternalException;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeNotFoundException;
import com.albanj.capitalize.capitalizeback.form.PostForm;
import com.albanj.capitalize.capitalizeback.log.LogMessageBuilder;
import com.albanj.capitalize.capitalizeback.service.PostService;
import com.albanj.capitalize.capitalizeback.service.UserService;
import com.albanj.capitalize.capitalizeback.validator.groups.ValidationOnRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/posts")
@Validated
@Slf4j
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping()
    public Page<PostDto> list(Authentication authentication, @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) Integer pageIndex, @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String owner, @RequestParam(required = false) Boolean unvalidated)
            throws IOException {
        return postService.getAll(pageIndex, pageSize, tags, owner, unvalidated);
    }

    @GetMapping("/{id}")
    public PostDto getOne(Authentication authentication, @PathVariable String id)
            throws IOException, CapitalizeBadRequestException, CapitalizeNotFoundException {
        Integer idInt = null;
        try {
            idInt = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            log.error(LogMessageBuilder.buildHeader(authentication) + " getOne Error while parsing postId=[{}]", id);
            throw new CapitalizeBadRequestException();
        }
        return postService.getOne(idInt);
    }

    @PostMapping()
    public PostDto create(Authentication authentication,
            @Validated(ValidationOnRequest.class) @RequestBody PostForm post) throws Exception {
        UserDto user = this.userService.getOneByEmailOrUsername(null, authentication.getName());
        return postService.create(user, post);
    }

    @PutMapping("/{id}")
    public PostDto update(Authentication authentication, @PathVariable Integer id, @Valid @RequestBody PostForm post,
            BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new CapitalizeBadRequestException();
        }
        UserDto user = this.userService.getOneByEmailOrUsername(null, authentication.getName());

        return postService.update(user, id, post);
    }

    @PatchMapping("/{id}/validate")
    @PreAuthorize("hasAuthority('ARCHITECT') || hasAuthority('ADMIN')")
    public PostDto validate(Authentication authentication, @PathVariable Integer id)
            throws CapitalizeNotFoundException {
        UserDto user = this.userService.getOneByEmailOrUsername(null, authentication.getName());

        return postService.validate(user, id);
    }

    @PatchMapping("/{id}/unvalidate")
    @PreAuthorize("hasAuthority('ARCHITECT') || hasAuthority('ADMIN')")
    public PostDto unvalidate(Authentication authentication, @PathVariable Integer id)
            throws CapitalizeNotFoundException {
        UserDto user = this.userService.getOneByEmailOrUsername(null, authentication.getName());

        return postService.unvalidate(user, id);
    }

    @PostMapping("/{id}/files")
    public FileDto createFile(Authentication authentication, @PathVariable Integer id,
            @RequestPart(value = "file", required = false) MultipartFile multipartFile,
            @RequestPart(value = "text", required = false) String text, @RequestPart("path") String path,
            @RequestPart("name") String name, @RequestPart("type") String type) throws CapitalizeNotFoundException,
            CapitalizeForbiddenException, CapitalizeInternalException, CapitalizeBadRequestException {
        FileDto fileDto = new FileDto(null, path, name, type);
        UserDto user = this.userService.getOneByEmailOrUsername(null, authentication.getName());
        return postService.createFile(user, id, fileDto, multipartFile, text);

    }

    @PutMapping("/{id}/files/{fileId}")
    public FileDto updateFile(Authentication authentication, @PathVariable Integer id, @PathVariable Integer fileId,
            @RequestPart(value = "file", required = false) MultipartFile multipartFile,
            @RequestPart(value = "text", required = false) String text, @RequestPart("id") String fileIdForm,
            @RequestPart("path") String path, @RequestPart("name") String name, @RequestPart("type") String type)
            throws CapitalizeGenericException {
        Integer fileIdParsed = Integer.parseInt(fileIdForm);
        if (fileId.equals(fileIdParsed)) {
            throw new CapitalizeBadRequestException();
        }

        FileDto fileDto = new FileDto(fileIdParsed, path, name, type);
        UserDto user = this.userService.getOneByEmailOrUsername(null, authentication.getName());

        return postService.updateFile(user, id, fileId, fileDto, multipartFile, text);
    }

    @DeleteMapping("/{id}/files/{fileId}")
    public void deleteFiles(Authentication authentication, @PathVariable Integer id, @PathVariable Integer fileId)
            throws CapitalizeGenericException {
        UserDto user = this.userService.getOneByEmailOrUsername(null, authentication.getName());
        postService.deleteFile(user, id, fileId);
    }

}
