package com.nishchay.blog.mappers;

import com.nishchay.blog.domain.dtos.AuthorDto;
import com.nishchay.blog.domain.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDto toDto(User user);
}
