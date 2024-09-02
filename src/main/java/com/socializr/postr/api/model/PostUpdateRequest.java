package com.socializr.postr.api.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUpdateRequest {
    @Size(max = 1000, message = "Post content too long")
    private String content;
}
