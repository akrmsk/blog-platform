package com.blogproject.blog.services.impl;
import com.blogproject.blog.domain.CreatePostRequest;
import com.blogproject.blog.domain.PostStatus;
import com.blogproject.blog.domain.UpdatePostRequest;
import com.blogproject.blog.domain.entities.Category;
import com.blogproject.blog.domain.entities.Post;
import com.blogproject.blog.domain.entities.Tag;
import com.blogproject.blog.domain.entities.User;
import com.blogproject.blog.repositories.PostRepository;
import com.blogproject.blog.services.CategoryService;
import com.blogproject.blog.services.PostService;
import com.blogproject.blog.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    @Autowired
    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagService tagService;
    private static final int WORDS_PER_MINUTE=200;

    @Override
    @Transactional(readOnly=true)
    public List<Post> getAllPosts(UUID categoryId, UUID tagId) {
        if(categoryId!=null && tagId!=null){
            Category category=categoryService.getCategoryById(categoryId);
            Tag tag=tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndCategoryAndTagsContaining(PostStatus.PUBLISHED,category,tag);
        }
        if(categoryId!=null){
            Category category=categoryService.getCategoryById(categoryId);
            return postRepository.findAllByStatusAndCategory(PostStatus.PUBLISHED,category);
        }
        if(tagId!=null){
            Tag tag=tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndTagsContaining(PostStatus.PUBLISHED,tag);
        }
        return postRepository.findAllByStatus(PostStatus.PUBLISHED);
    }

    @Override
    public List<Post> getDraftPosts(User user) {
        List<Post> draftPosts= postRepository.findAllByAuthorAndStatus(user,PostStatus.DRAFT);
        return draftPosts;
    }
    @Transactional
    @Override
    public Post createPost(User user, CreatePostRequest postRequest) {
        Post newPost=new Post();
        newPost.setTitle(postRequest.getTitle());
        newPost.setContent(postRequest.getContent());
        newPost.setAuthor(user);
        newPost.setStatus(postRequest.getStatus());
        newPost.setReadingTime(calculateReadingTime(postRequest.getContent()));
        newPost.setCategory(categoryService.getCategoryById(postRequest.getCategoryId()));
        Set<UUID> tagIds= postRequest.getTagIds();
        List<Tag> tags=tagService.getTagByIds(tagIds);
        newPost.setTags(new HashSet<>(tags));
        return postRepository.save(newPost);
    }

    @Override
    @Transactional
    public Post updatePost(User loggedInUser, UpdatePostRequest updatePostRequest, UUID postId) {
        Post currentPost=postRepository.findById(postId).orElseThrow(()->new EntityNotFoundException("post not found"));
        currentPost.setTitle(updatePostRequest.getTitle());
        currentPost.setContent(updatePostRequest.getContent());
        currentPost.setAuthor(loggedInUser);
        currentPost.setStatus(updatePostRequest.getStatus());
        currentPost.setCategory(categoryService.getCategoryById(updatePostRequest.getCategoryId()));
        currentPost.setReadingTime(calculateReadingTime(updatePostRequest.getContent()));
        Set<UUID> tagIds= updatePostRequest.getTagIds();
        List<Tag> tags=tagService.getTagByIds(tagIds);
        currentPost.setTags(new HashSet<>(tags));
        return postRepository.save(currentPost);
    }

    @Override
    public Post getPostById(UUID id) {
        Post post=postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Post with id:"+id+" not found"));
        return post;
    }

    @Override
    public void deletePost(UUID id) {
        if(postRepository.findById(id).isPresent()){
            postRepository.deleteById(id);
        }else{
            throw new RuntimeException("Post with id:"+id+" not found");
        }
    }


    private Integer calculateReadingTime(String content) {
        if (content == null || content.isEmpty()) {
            return 0;
        }

        int wordCount = content.trim().split("\\s+").length;
        return (int) Math.ceil((double) wordCount / WORDS_PER_MINUTE);
    }
}
