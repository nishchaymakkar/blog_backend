package com.nishchay.blog.mappers;

import com.nishchay.blog.domain.PostStatus;
import com.nishchay.blog.domain.dtos.TagResponse;
import com.nishchay.blog.domain.entities.Post;
import com.nishchay.blog.domain.entities.Tag;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

    @Mapping(target= "postCount", source = "posts",qualifiedByName = "calculatePostCount")
    TagResponse toTagResponse(Tag tag);

    @Named("calculatePostCount")
    default Integer calculatePostCount(Set<Post> posts) {
        if (posts == null){
            return 0;
        }
      return (int) posts.stream()
                .filter(post -> PostStatus.PUBLISHED.equals(post.getStatus()))
                .count();
    }
}
