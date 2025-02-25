package com.nishchay.blog.controllers;

import com.nishchay.blog.domain.dtos.PostDto;
import com.nishchay.blog.domain.entities.Post;
import com.nishchay.blog.mappers.PostMapper;
import com.nishchay.blog.services.PostService;
import jdk.jfr.Frequency;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(required = false)UUID categoryId,
            @RequestParam(required = false)UUID tagId
            ){
       List<Post> posts = postService.getAllPosts(categoryId,tagId);
        List<PostDto> postDtos= posts.stream().map(postMapper::toDto).toList();

        return ResponseEntity.ok(postDtos);
    }
}
