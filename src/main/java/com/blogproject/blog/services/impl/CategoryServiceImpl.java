package com.blogproject.blog.services.impl;

import com.blogproject.blog.domain.entities.Category;
import com.blogproject.blog.repositories.CategoryRepository;
import com.blogproject.blog.services.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAllBypostCount();
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        if(categoryRepository.existsByNameIgnoreCase(category.getName())){
            throw new IllegalArgumentException("Category with name : "+category.getName()+"already exists");
        }
        return categoryRepository.save(category);
    }
}
