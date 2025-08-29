package com.blogproject.blog.services;

import com.blogproject.blog.domain.entities.User;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID id);
}
