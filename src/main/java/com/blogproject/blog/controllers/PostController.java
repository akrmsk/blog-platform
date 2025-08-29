package com.blogproject.blog.controllers;


import com.blogproject.blog.domain.CreatePostRequest;
import com.blogproject.blog.domain.UpdatePostRequest;
import com.blogproject.blog.domain.dtos.CreatePostRequestDto;
import com.blogproject.blog.domain.dtos.PostDto;
import com.blogproject.blog.domain.dtos.UpdatePostRequestDto;
import com.blogproject.blog.domain.entities.Post;
import com.blogproject.blog.domain.entities.User;
import com.blogproject.blog.mappers.PostMapper;
import com.blogproject.blog.services.PostService;
import com.blogproject.blog.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/posts")
public class PostController {

    @Autowired
    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(@RequestParam(required = false)UUID categoryId,@RequestParam(required = false)UUID tagId){
         List<Post> posts =postService.getAllPosts(categoryId,tagId);
         List<PostDto> dtoPosts= posts.stream().map(post -> postMapper.toDto(post)).toList();
         return new ResponseEntity<>(dtoPosts, HttpStatus.OK);
    }

    @GetMapping(path = "/drafts")
    public ResponseEntity<List<PostDto>> getDrafts(@RequestAttribute UUID userId){
        User loggedInUser= userService.getUserById(userId);
        List<Post> draftPosts= postService.getDraftPosts(loggedInUser);
        List<PostDto> draftDtoPosts=draftPosts.stream().map(post -> postMapper.toDto(post)).toList();
        return new ResponseEntity<>(draftDtoPosts,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody CreatePostRequestDto createPostRequestDto,@RequestAttribute UUID userId){
        User loggedInUser= userService.getUserById(userId);
        CreatePostRequest createPostRequest=postMapper.toCreatePostRequest(createPostRequestDto);
        Post newPost=postService.createPost(loggedInUser,createPostRequest);
        return new ResponseEntity<>(postMapper.toDto(newPost),HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody UpdatePostRequestDto updatePostRequestDto, @RequestAttribute UUID userId, @PathVariable UUID id ){
        User loggedInUser= userService.getUserById(userId);
        UpdatePostRequest updatePostRequest=postMapper.toUpdatePostRequest(updatePostRequestDto);
        Post updatedPost=postService.updatePost(loggedInUser,updatePostRequest,id);
        return new ResponseEntity<>(postMapper.toDto(updatedPost),HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable UUID id){
        Post post=postService.getPostById(id);
        return new ResponseEntity<>(postMapper.toDto(post),HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable UUID id){
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
