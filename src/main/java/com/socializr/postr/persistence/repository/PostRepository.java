package com.socializr.postr.persistence.repository;

import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import com.socializr.postr.persistence.entity.PostEntity;
import reactor.core.publisher.Flux;

public interface PostRepository extends R2dbcRepository<PostEntity, UUID> {
    Flux<PostEntity> findAllByOrderByCreationTimestampDesc(Pageable pageable);
}
