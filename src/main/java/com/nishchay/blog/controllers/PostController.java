package com.nishchay.blog.controllers;

import com.nishchay.blog.domain.CreatePostRequest;
import com.nishchay.blog.domain.dtos.CreatePostRequestDto;
import com.nishchay.blog.domain.dtos.PostDto;
import com.nishchay.blog.domain.entities.Post;
import com.nishchay.blog.domain.entities.User;
import com.nishchay.blog.mappers.PostMapper;
import com.nishchay.blog.services.PostService;
import com.nishchay.blog.services.UserService;
import jdk.jfr.Frequency;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(required = false)UUID categoryId,
            @RequestParam(required = false)UUID tagId
            ){
       List<Post> posts = postService.getAllPosts(categoryId,tagId);
        List<PostDto> postDtos= posts.stream().map(postMapper::toDto).toList();

        return ResponseEntity.ok(postDtos);
    }

    @GetMapping(path = "/drafts")
    public ResponseEntity<List<PostDto>> getDrafts(@RequestAttribute UUID userId){
       User loggedInUser = userService.getUserById(userId);
       List<Post> draftPosts = postService.getDrafts(loggedInUser);
       List<PostDto> postDtos = draftPosts.stream().map(postMapper::toDto).toList();
       return ResponseEntity.ok(postDtos);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @RequestBody CreatePostRequestDto createPostRequestDto,
            @RequestAttribute UUID userId){
        User logedInUser = userService.getUserById(userId);
       CreatePostRequest createPostRequest = postMapper.toCreatePostRequest(createPostRequestDto);
       Post createdPost = postService.createPost(logedInUser,createPostRequest);
       PostDto createdPostDto = postMapper.toDto(createdPost);
       return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
    }


}
