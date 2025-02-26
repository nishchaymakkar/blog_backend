package com.nishchay.blog.services;

import com.nishchay.blog.domain.CreatePostRequest;
import com.nishchay.blog.domain.entities.Post;
import com.nishchay.blog.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<Post> getAllPosts(UUID categoryId, UUID tagId);
    List<Post> getDrafts(User user);
    Post createPost(User user, CreatePostRequest createPostRequest);
}
