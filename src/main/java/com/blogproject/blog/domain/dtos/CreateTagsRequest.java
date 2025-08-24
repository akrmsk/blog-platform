package com.blogproject.blog.domain.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTagsRequest {

    @NotEmpty(message = "Atleast one tag name is required")
    @Size(min = 2,max = 50,message = "Tag name must be between {min} and {max} characters")
    @Pattern(regexp = "^[\\w\\s-]+$", message = "category can only contain letters,numbers,spaces and hyphens")
    private Set<String> names;

}
