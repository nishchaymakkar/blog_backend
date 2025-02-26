package com.nishchay.blog.mappers;

import com.nishchay.blog.domain.CreatePostRequest;
import com.nishchay.blog.domain.dtos.CreatePostRequestDto;
import com.nishchay.blog.domain.dtos.PostDto;
import com.nishchay.blog.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target= "author", source = "author")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "tags",source = "tags")
    PostDto toDto(Post post);

    CreatePostRequest toCreatePostRequest(CreatePostRequestDto dto);
}
