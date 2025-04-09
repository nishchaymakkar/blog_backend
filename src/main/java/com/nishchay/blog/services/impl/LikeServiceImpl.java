package com.nishchay.blog.services.impl;


import com.nishchay.blog.domain.entities.Likes;
import com.nishchay.blog.domain.entities.User;
import com.nishchay.blog.repository.LikeRepository;
import com.nishchay.blog.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;



    @Override
    public void likePost(UUID userId, UUID postId) {
        if (!likeRepository.existsByUserIdAndPostId(userId,postId)){
            likeRepository.save(new Likes(userId,postId));
        }
    }

    @Override
    @Transactional
    public void deleteLike(UUID userId,UUID postId) {
        if (likeRepository.existsByUserIdAndPostId(userId, postId)) {
            likeRepository.deleteByUserIdAndPostId(userId, postId);
        }
    }

    @Override
    public long getLikeCount(UUID postId) {
        return likeRepository.countByPostId(postId);
    }

    @Override
    public boolean hasUserLiked(UUID userId, UUID postId) {
        return likeRepository.existsByUserIdAndPostId(userId, postId);
    }

    @Override
    public List<User> getUserWhoLiked(UUID postId) {
        return likeRepository.findUserIdsByPostId(postId);
    }
}
