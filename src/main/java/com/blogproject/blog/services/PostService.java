package com.blogproject.blog.services;


import com.blogproject.blog.domain.CreatePostRequest;
import com.blogproject.blog.domain.UpdatePostRequest;
import com.blogproject.blog.domain.entities.Post;
import com.blogproject.blog.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {

    List<Post> getAllPosts(UUID categoryId,UUID tagId);
    List<Post> getDraftPosts(User user);

    Post createPost(User user, CreatePostRequest postRequest);

    Post updatePost(User loggedInUser, UpdatePostRequest updatePostRequest, UUID postId);

    Post getPostById(UUID id);

    void deletePost(UUID id);
}
