package com.socializr.postr.api.model;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreationRequest {
    private UUID userId;
    private String content;
}
