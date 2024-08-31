package com.socializr.postr.persistence.entity;

import java.time.ZonedDateTime;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Getter;
import lombok.Setter;

@Table("posts")
@Getter
@Setter
public class PostEntity {
    @Id
    private UUID id;
    private UUID userId;
    private String content;
    private ZonedDateTime creationTimestamp;
    private ZonedDateTime lastUpdateTimestamp;
}
