package com.blogproject.blog.controllers;


import com.blogproject.blog.domain.dtos.PostDto;
import com.blogproject.blog.domain.entities.Post;
import com.blogproject.blog.mappers.PostMapper;
import com.blogproject.blog.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/posts")
public class PostController {

    @Autowired
    private final PostService postService;
    private final PostMapper postMapper;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(@RequestParam(required = false)UUID categoryId,@RequestParam(required = false)UUID tagId){
         List<Post> posts =postService.getAllPosts(categoryId,tagId);
         List<PostDto> dtoPosts= posts.stream().map(post -> postMapper.toDto(post)).toList();
         return new ResponseEntity<>(dtoPosts, HttpStatus.OK);
    }
}
