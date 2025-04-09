package com.nishchay.blog.repository;

import com.nishchay.blog.domain.entities.Likes;
import com.nishchay.blog.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<Likes,Long> {

    boolean existsByUserIdAndPostId(UUID userId, UUID postId);

    void deleteByUserIdAndPostId(UUID userId, UUID postId);

    long countByPostId(UUID postId);

    @Query("SELECT l.user FROM Likes l WHERE l.post.Id = :postId")
    List<User> findUserIdsByPostId(@Param("postId") UUID postId);

}
