package com.blogproject.blog.controllers;

import com.blogproject.blog.domain.dtos.CreateTagsRequest;
import com.blogproject.blog.domain.dtos.TagResponse;
import com.blogproject.blog.domain.entities.Tag;
import com.blogproject.blog.mappers.TagMapper;
import com.blogproject.blog.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {
    @Autowired
    private final TagService tagService;

    private final TagMapper tagMapper;
    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags(){
        List<Tag> tags=tagService.getTags();
        List<TagResponse> tagResponses= tags.stream().map(tag ->tagMapper.toTagResponse(tag)).toList();
        return ResponseEntity.ok(tagResponses);
    }

    @PostMapping
    public ResponseEntity<List<TagResponse>> createTags(@RequestBody CreateTagsRequest createTagsRequest){
        List<Tag> savedTags= tagService.createTags(createTagsRequest.getNames());
        List<TagResponse> createdTagResponses= savedTags.stream().map(tag ->tagMapper.toTagResponse(tag)).toList();
        return new ResponseEntity<>(createdTagResponses, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id){
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }


}
