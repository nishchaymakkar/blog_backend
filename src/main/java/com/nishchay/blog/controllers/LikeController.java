package com.nishchay.blog.controllers;

import com.nishchay.blog.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts/like")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/likePost")
    public ResponseEntity<String> likePost(
            @RequestParam("postId") UUID postId,
            @RequestParam("userId") UUID userId) {
        likeService.likePost(userId, postId);
        return ResponseEntity.ok("Done");
    }

    @DeleteMapping("/deleteLike")
    public ResponseEntity<String> deleteLike(
            @RequestParam("postId") UUID postId,
            @RequestParam("userId") UUID userId) {
        likeService.deleteLike(userId, postId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getLikeCount(@RequestParam UUID postId) {
        long likeCount = likeService.getLikeCount(postId);
        return ResponseEntity.ok(likeCount);
    }

    @GetMapping("/hasUserLiked")
    public ResponseEntity<Boolean> hasUserLikedPost(
            @RequestParam("postId") UUID postId,
            @RequestParam("userId") UUID userId) {
        boolean hasLiked = likeService.hasUserLiked(userId, postId);
        return ResponseEntity.ok(hasLiked);
    }

    @GetMapping("/listOfLikes")
    public ResponseEntity<List<UUID>> getUserWhoLiked(@RequestParam("postId") UUID postId) {
        List<UUID> userIds = likeService.getUserWhoLiked(postId);
        return ResponseEntity.ok(userIds);
    }
}
