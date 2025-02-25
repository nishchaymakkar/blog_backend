package com.nishchay.blog.controllers;

import com.nishchay.blog.domain.dtos.CreateTagsRequest;
import com.nishchay.blog.domain.dtos.TagResponse;
import com.nishchay.blog.domain.entities.Tag;
import com.nishchay.blog.mappers.TagMapper;
import com.nishchay.blog.repository.TagRepository;
import com.nishchay.blog.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags(){
        List<Tag> tags = tagService.getTags();

        List<TagResponse> tagResponses = tags.stream().map(tagMapper::toTagResponse).toList();
        return ResponseEntity.ok(tagResponses);
    }

    @PostMapping
    public ResponseEntity<List<TagResponse>> createTags(@RequestBody CreateTagsRequest createTagsRequest){
        List<Tag> savedTags =tagService.createTags(createTagsRequest.getNames());
       List<TagResponse> createdTagResponse =  savedTags.stream().map(tag -> tagMapper.toTagResponse(tag)).toList();
       return new ResponseEntity<>(
               createdTagResponse,
               HttpStatus.CREATED
       );

    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id){
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
