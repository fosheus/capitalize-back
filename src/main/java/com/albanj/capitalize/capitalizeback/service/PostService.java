package com.albanj.capitalize.capitalizeback.service;

import java.io.IOException;
import java.util.List;

import com.albanj.capitalize.capitalizeback.dto.FileDto;
import com.albanj.capitalize.capitalizeback.dto.PostDto;
import com.albanj.capitalize.capitalizeback.dto.UserDto;
import com.albanj.capitalize.capitalizeback.entity.Post;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeBadRequestException;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeForbiddenException;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeGenericException;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeInternalException;
import com.albanj.capitalize.capitalizeback.exception.CapitalizeNotFoundException;
import com.albanj.capitalize.capitalizeback.form.PostForm;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

        PostDto getOne(Integer id) throws IOException, CapitalizeNotFoundException;

        Page<PostDto> getAll(Integer pageIndex, Integer pageSize, List<String> tags, String owner, Boolean unvalidated)
                        throws IOException;

        PostDto create(UserDto userDto, PostForm post) throws Exception;

        PostDto update(UserDto userDto, Integer id, PostForm post) throws CapitalizeGenericException;

        PostDto validate(UserDto userDto, Integer id) throws CapitalizeNotFoundException;

        PostDto unvalidate(UserDto userDto, Integer id) throws CapitalizeNotFoundException;

        FileDto createFile(UserDto userDto, Integer postId, FileDto fileDto, MultipartFile file, String text)
                        throws CapitalizeNotFoundException, CapitalizeForbiddenException, CapitalizeInternalException,
                        CapitalizeBadRequestException;

        FileDto updateFile(UserDto userDto, Integer postId, Integer fileId, FileDto fileDto, MultipartFile file,
                        String text) throws CapitalizeGenericException;

        void deleteFile(UserDto userDto, Integer postId, Integer fileId) throws CapitalizeGenericException;

        void checkUserHasRightOrThrowForbidden(UserDto user, Post post, List<String> overrides)
                        throws CapitalizeForbiddenException;

}
