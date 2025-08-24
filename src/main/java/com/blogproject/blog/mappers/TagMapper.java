package com.blogproject.blog.mappers;

import com.blogproject.blog.domain.PostStatus;
import com.blogproject.blog.domain.dtos.TagResponse;
import com.blogproject.blog.domain.entities.Post;
import com.blogproject.blog.domain.entities.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    TagResponse toTagResponse(Tag tag);

    @Named("calculatePostCount")
    default Integer calculatePostCount(Set<Post> posts){
        if(null==posts){
            return 0;
        }
        return (int)posts.stream()
                .filter(post-> PostStatus.PUBLISHED.equals(post.getStatus()))
                .count();
    }
}
