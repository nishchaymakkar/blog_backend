package com.nishchay.blog.services.impl;

import com.nishchay.blog.domain.entities.Tag;
import com.nishchay.blog.repository.TagRepository;
import com.nishchay.blog.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public Tag getTagById(UUID id) {
        return tagRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Tag not found with id"+ id));
    }

    @Override
    public List<Tag> getTagByIds(Set<UUID> ids) {
       List<Tag> foundTags = tagRepository.findAllById(ids);
       if (foundTags.size() != ids.size()){
           throw new EntityNotFoundException("Not all speciefied tag IDs exist");
       }
       return foundTags;
    }

    @Transactional
    @Override
    public void deleteTag(UUID id) {
        tagRepository.findById(id).ifPresent(tag -> {
            if(!tag.getPosts().isEmpty()) {
                throw new IllegalStateException("Cannot delete tag with posts");
            }
            tagRepository.deleteById(id);
        });
    }

    @Transactional
    @Override
    public List<Tag> createTags(Set<String> tagNames) {
        List<Tag> existingTags = tagRepository.findByNameIn(tagNames);

       Set<String> existingTagNames = existingTags
               .stream()
               .map(Tag::getName)
               .collect(Collectors.toSet());

          List<Tag> newTags = tagNames.stream()
                   .filter(name -> !existingTagNames.contains(name))
                   .map(name -> Tag.builder()
                           .name(name)
                           .posts(new HashSet<>())
                           .build())
                   .toList();

          List<Tag> savedTags = new ArrayList<>();
          if (!newTags.isEmpty()) {
              tagRepository.saveAll(newTags);
          }
          savedTags.addAll(existingTags);
          return savedTags;
    }

    @Override
    public List<Tag> getTags() {
        return tagRepository.findAllWithPostCount();
    }
}
