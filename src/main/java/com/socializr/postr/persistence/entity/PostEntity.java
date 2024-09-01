package com.socializr.postr.persistence.entity;

import java.time.ZonedDateTime;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Getter;
import lombok.Setter;

@Table("posts")
@Getter
@Setter
public class PostEntity {
    @Id
    private UUID id;
    @Column("user_id")
    private UUID userId;
    private String content;
    @Column("creation_timestamp")
    private ZonedDateTime creationTimestamp;
    @Column("last_update_timestamp")
    private ZonedDateTime lastUpdateTimestamp;
}
