package com.blogproject.blog.services;


import com.blogproject.blog.domain.dtos.PostDto;
import com.blogproject.blog.domain.entities.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {

    List<Post> getAllPosts(UUID categoryId,UUID tagId);
}
