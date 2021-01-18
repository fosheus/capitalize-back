package com.albanj.capitalize.capitalizeback.resource;

import com.albanj.capitalize.capitalizeback.dto.PostDto;
import com.albanj.capitalize.capitalizeback.exception.BadRequestException;
import com.albanj.capitalize.capitalizeback.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public List<PostDto> list(@RequestParam(required = false) List<String> tags,@RequestParam(required=false) Integer page, @RequestParam(required = false) Integer size) {
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
    public List<PostDto> listUnvalidated(@RequestParam(required = false) List<String> tags,@RequestParam(required=false) Integer page, @RequestParam(required = false) Integer size) {
        return postService.getAll(page,size,tags);
    }

    @PostMapping("/")
    public PostDto create(@RequestBody PostDto post) throws Exception {
        return postService.create(post);
    }


}
