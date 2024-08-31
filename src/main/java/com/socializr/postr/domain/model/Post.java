package com.socializr.postr.domain.model;

import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Post {
    private UUID id;
    private UUID userId;
    private String content;
    private ZonedDateTime creationTimestamp;
    private ZonedDateTime lastUpdateTimestamp;
}
