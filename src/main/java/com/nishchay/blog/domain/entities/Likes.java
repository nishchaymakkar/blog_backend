package com.nishchay.blog.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Likes",uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "post_id"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Likes(UUID userId, UUID postId) {
            this.user = new User();
            this.user.setId(userId);
            
            this.post = new Post();
            this.post.setId(postId);
    }
}
