package com.socializr.postr.api.model;

import java.util.UUID;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreationRequest {
    private UUID userId;
    @Size(max = 1000, message = "Post content too long")
    private String content;
}
