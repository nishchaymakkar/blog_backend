package com.nishchay.blog.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface LikeService {
    void likePost(UUID userId, UUID postId);

    void deleteLike(UUID userId,UUID postId);

    long getLikeCount(UUID postId);

    boolean hasUserLiked(UUID userId, UUID postId);

    List<UUID> getUserWhoLiked(UUID postId);
}
