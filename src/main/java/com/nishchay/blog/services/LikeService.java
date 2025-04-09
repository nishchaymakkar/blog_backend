package com.nishchay.blog.services;

import com.nishchay.blog.domain.dtos.AuthorDto;
import com.nishchay.blog.domain.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface LikeService {
    void likePost(UUID userId, UUID postId);

    void deleteLike(UUID userId,UUID postId);

    long getLikeCount(UUID postId);

    boolean hasUserLiked(UUID userId, UUID postId);

    List<User> getUserWhoLiked(UUID postId);
}
