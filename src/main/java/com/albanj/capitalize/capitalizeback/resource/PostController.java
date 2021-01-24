package com.albanj.capitalize.capitalizeback.resource;

import com.albanj.capitalize.capitalizeback.dto.PostDto;
import com.albanj.capitalize.capitalizeback.exception.BadRequestException;
import com.albanj.capitalize.capitalizeback.form.PostForm;
import com.albanj.capitalize.capitalizeback.service.PostService;
import com.albanj.capitalize.capitalizeback.validator.groups.ValidationOnRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public List<PostDto> list(@RequestParam(required = false) List<String> tags,@RequestParam(required=false) Integer page, @RequestParam(required = false) Integer size) throws IOException {
        return postService.getAll(page,size,tags);
    }

    @GetMapping("/{id}")
    public PostDto getOne(@PathVariable String id) throws IOException {
        Integer idInt=null;
        try {
            idInt = Integer.parseInt(id);
        }catch (NumberFormatException e){
            throw new BadRequestException("L'identifiant n'est pas au format entier");
        }
        return postService.getOne(idInt);
    }

    @GetMapping("/unvalidated")
    @PreAuthorize("hasAuthority('ARCHITECT')")
    public List<PostDto> listUnvalidated(@RequestParam(required = false) List<String> tags,@RequestParam(required=false) Integer page, @RequestParam(required = false) Integer size) throws IOException {
        return postService.getAll(page,size,tags);
    }

    @PostMapping("/")
    public PostDto create(Authentication authentication, @RequestBody @Validated(ValidationOnRequest.class) PostForm post) throws Exception {
        return postService.create(authentication,post);
    }

    @PutMapping("/{id}")
    public PostDto update(@PathVariable Integer id,@RequestBody @Validated(ValidationOnRequest.class) PostForm post) throws Exception {
        return postService.update(id,post);
    }

    @PatchMapping("/{id}/validate")
    public PostDto validate(Authentication authentication,@PathVariable Integer id) {
        return postService.validate(authentication,id);
    }


}
