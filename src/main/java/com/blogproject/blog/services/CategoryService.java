package com.blogproject.blog.services;

import com.blogproject.blog.domain.entities.Category;

import java.util.List;

public interface CategoryService {
    List<Category> listCategories();

    Category createCategory(Category category);
}
