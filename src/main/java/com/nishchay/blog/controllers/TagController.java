package com.nishchay.blog.controllers;

import com.nishchay.blog.domain.dtos.TagResponse;
import com.nishchay.blog.domain.entities.Tag;
import com.nishchay.blog.mappers.TagMapper;
import com.nishchay.blog.repository.TagRepository;
import com.nishchay.blog.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags(){
        List<Tag> tags = tagService.getTags();
        return ResponseEntity.ok(tagResponses);
    }
}
