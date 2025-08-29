package com.blogproject.blog.services.impl;

import com.blogproject.blog.domain.entities.User;
import com.blogproject.blog.repositories.UserRepository;
import com.blogproject.blog.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("User not found with id:"+id));
    }
}
