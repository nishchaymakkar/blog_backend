package com.nishchay.blog.services;

import com.nishchay.blog.domain.CreatePostRequest;
import com.nishchay.blog.domain.UpdatePostRequest;
import com.nishchay.blog.domain.dtos.PaginationDTO;
import com.nishchay.blog.domain.entities.Post;
import com.nishchay.blog.domain.entities.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface PostService {
    Post getPost(UUID id);
    Page<Post> getAllPosts(PaginationDTO paginationDTO, UUID categoryId, UUID tagId);
    List<Post> getDrafts(User user);
    Post createPost(User user, CreatePostRequest createPostRequest);

    Post updatePost(UUID id, UpdatePostRequest updatePostRequest);
    Post deletePost(UUID id);
}
