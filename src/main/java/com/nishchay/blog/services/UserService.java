package com.nishchay.blog.services;

import com.nishchay.blog.domain.entities.User;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID id);
}
