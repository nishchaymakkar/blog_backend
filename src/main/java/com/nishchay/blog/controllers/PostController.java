package com.nishchay.blog.controllers;

import com.nishchay.blog.domain.CreatePostRequest;
import com.nishchay.blog.domain.UpdatePostRequest;
import com.nishchay.blog.domain.dtos.CreatePostRequestDto;
import com.nishchay.blog.domain.dtos.PostDto;
import com.nishchay.blog.domain.dtos.UpdatePostRequestDto;
import com.nishchay.blog.domain.entities.Post;
import com.nishchay.blog.domain.entities.User;
import com.nishchay.blog.mappers.PostMapper;
import com.nishchay.blog.services.PostService;
import com.nishchay.blog.services.UserService;
import jakarta.validation.Valid;
import jdk.jfr.Frequency;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
           @Valid @RequestBody CreatePostRequestDto createPostRequestDto,
            @RequestAttribute UUID userId){
        User logedInUser = userService.getUserById(userId);
       CreatePostRequest createPostRequest = postMapper.toCreatePostRequest(createPostRequestDto);
       Post createdPost = postService.createPost(logedInUser,createPostRequest);
       PostDto createdPostDto = postMapper.toDto(createdPost);
       return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
    }

    @PutMapping(path= "/{id}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable UUID id,
         @Valid @RequestBody UpdatePostRequestDto updatePostRequestDto
    ){
       UpdatePostRequest updatePostRequest = postMapper.toUpdatePostRequest(updatePostRequestDto);
       Post updatedPost = postService.updatePost(id,updatePostRequest);

       PostDto updatedPostDto = postMapper.toDto(updatedPost);
       return ResponseEntity.ok(updatedPostDto);
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<PostDto> getPost(
            @PathVariable UUID id
    ){
       Post post =  postService.getPost(id);
       PostDto postDto = postMapper.toDto(post);
       return ResponseEntity.ok(postDto);
    }
}
