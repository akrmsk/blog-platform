package com.blogproject.blog.controllers;


import com.blogproject.blog.domain.dtos.CategoryDto;
import com.blogproject.blog.domain.dtos.CreateCategoryRequest;
import com.blogproject.blog.domain.entities.Category;
import com.blogproject.blog.mappers.CategoryMapper;
import com.blogproject.blog.services.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> listCategories(){
        List<CategoryDto> categories= categoryService.listCategories()
                .stream().map(categoryMapper::toDto)
                .toList();
        return ResponseEntity.ok(categories);
    }

    //post categories
    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest){
        Category categoryToCreate=categoryMapper.toEntity(createCategoryRequest);
        Category savedCategory=categoryService.createCategory(categoryToCreate);
        return new ResponseEntity<>(categoryMapper.toDto(savedCategory), HttpStatus.CREATED);
    }

    //delete categories
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
