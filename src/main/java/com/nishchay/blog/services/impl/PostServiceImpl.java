package com.nishchay.blog.services.impl;

import com.nishchay.blog.domain.CreatePostRequest;
import com.nishchay.blog.domain.PostStatus;
import com.nishchay.blog.domain.UpdatePostRequest;
import com.nishchay.blog.domain.entities.Category;
import com.nishchay.blog.domain.entities.Post;
import com.nishchay.blog.domain.entities.Tag;
import com.nishchay.blog.domain.entities.User;
import com.nishchay.blog.repository.PostRepository;
import com.nishchay.blog.services.CategoryService;
import com.nishchay.blog.services.PostService;
import com.nishchay.blog.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagService tagService;
    private static final int WORDS_PER_MINUTE= 200;


    @Override
    public Post getPost(UUID id) {
       return postRepository.findById(id)
               .orElseThrow(()-> new EntityNotFoundException("Post does not exist with id" + id));
    }

    @Override
    @jakarta.transaction.Transactional
    public Post updatePost(UUID id, UpdatePostRequest updatePostRequest) {
       Post existingPost =  postRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Post does not exist with id" + id));
       existingPost.setTitle(updatePostRequest.getTitle());
       String postContent = updatePostRequest.getContent();
               existingPost.setContent(postContent);
       existingPost.setStatus(updatePostRequest.getStatus());
       existingPost.setReadingTime(calculateReadingTime(postContent));

       UUID updatePostRequestCategoryId = updatePostRequest.getCategoryId();

       if (!existingPost.getCategory().getId().equals(updatePostRequestCategoryId)){
          Category newCategory = categoryService.getCategoryById(updatePostRequestCategoryId);
          existingPost.setCategory(newCategory);
       }

       Set<UUID> existingTagsIds =  existingPost.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
       Set<UUID> updatePostRequestTagIds = updatePostRequest.getTagIds();

       if (!existingTagsIds.equals(updatePostRequestTagIds)){
         List<Tag> newTags =  tagService.getTagByIds(updatePostRequestTagIds);
         existingPost.setTags(new HashSet<>(newTags));
       }

       return postRepository.save(existingPost);
    }


    @Override
    @jakarta.transaction.Transactional
    public Post createPost(User user, CreatePostRequest createPostRequest) {
        Post newPost = new Post();
        newPost.setTitle(createPostRequest.getTitle());
        newPost.setContent(createPostRequest.getContent());
        newPost.setStatus(createPostRequest.getStatus());
        newPost.setAuthor(user);
        newPost.setReadingTime(calculateReadingTime(createPostRequest.getContent()));

        Category category = categoryService.getCategoryById(createPostRequest.getCategoryId());
        newPost.setCategory(category);

        Set<UUID> tagIds = createPostRequest.getTagIds();
        List<Tag> tags = tagService.getTagByIds(tagIds);
        newPost.setTags(new HashSet<>(tags));

        return postRepository.save(newPost);
    }
    private Integer calculateReadingTime(String content){
        if (content == null || content.isEmpty()){
            return 0;
        }
       int wordCount = content.trim().split("\\s+").length;
      return (int) Math.ceil((double) wordCount / WORDS_PER_MINUTE);
    }

    @Override
    public List<Post> getDrafts(User user) {
       return postRepository.findAllByAuthorAndStatus(user,PostStatus.DRAFT);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPosts(UUID categoryId, UUID tagId) {

        if (categoryId != null && tagId != null){
            Category category = categoryService.getCategoryById(categoryId);
            Tag tag = tagService.getTagById(tagId);
            return  postRepository.findAllByStatusAndCategoryAndTagsContaining(
                    PostStatus.PUBLISHED,
                    category,
                    tag
            );
        }

        if (categoryId != null){
            Category category = categoryService.getCategoryById(categoryId);
            return postRepository.findAllByStatusAndCategory(
                    PostStatus.PUBLISHED,
                    category
            );
        }

        if (tagId != null){
            Tag tag = tagService.getTagById(tagId);
            return  postRepository.findAllByStatusAndTagsContaining(
                    PostStatus.PUBLISHED,
                    tag
            );
        }
        return postRepository.findAllByStatus(PostStatus.PUBLISHED);
    }

    @Override
    public Post deletePost(UUID id) {
         postRepository.deleteById(id);
         return null;
    }
}
