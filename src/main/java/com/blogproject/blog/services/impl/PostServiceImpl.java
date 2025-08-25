package com.blogproject.blog.services.impl;
import com.blogproject.blog.domain.PostStatus;
import com.blogproject.blog.domain.entities.Category;
import com.blogproject.blog.domain.entities.Post;
import com.blogproject.blog.domain.entities.Tag;
import com.blogproject.blog.repositories.PostRepository;
import com.blogproject.blog.services.CategoryService;
import com.blogproject.blog.services.PostService;
import com.blogproject.blog.services.TagService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    @Autowired
    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagService tagService;
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
}
