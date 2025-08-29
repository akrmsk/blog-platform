package com.blogproject.blog.domain.dtos;

import com.blogproject.blog.domain.PostStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePostRequestDto {

    @NotNull(message = "POST ID IS REQUIRED")
    private UUID id;

    @NotBlank(message = "Title cant be blank")
    @Size(min = 3,max = 100, message = "Title size must be between ${min} and ${max} characters")
    private String title;
    @NotBlank(message = "Content cant be blank")
    @Size(min = 10,max = 50000, message = "Content size must be between ${min} and ${max} characters")
    private String content;
    @NotNull(message = "Category Id must be filled")
    private UUID categoryId;

    @NotNull(message = "max 10 tags are allowed")
    private Set<UUID> tagId=new HashSet<>();
    @NotNull(message = "post status is required")
    private PostStatus status;
}
