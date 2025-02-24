package com.nishchay.blog.services;

import com.nishchay.blog.domain.entities.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getTags();
}
