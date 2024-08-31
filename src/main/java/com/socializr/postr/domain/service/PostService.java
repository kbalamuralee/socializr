package com.socializr.postr.domain.service;

import java.time.ZonedDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.socializr.postr.domain.mapper.PostDomainMapper;
import com.socializr.postr.domain.model.Post;
import com.socializr.postr.persistence.entity.PostEntity;
import com.socializr.postr.persistence.repository.PostRepository;
import reactor.core.publisher.Mono;

@Service
public class PostService {
    @Autowired
    private PostDomainMapper postDomainMapper;
    @Autowired
    private PostRepository postRepository;

    public Mono<Post> createPost(Post post) {
        post.setId(UUID.randomUUID());
        post.setCreationTimestamp(ZonedDateTime.now());
        post.setLastUpdateTimestamp(ZonedDateTime.now());
        PostEntity postEntityToCreate = postDomainMapper.mapPostToPostEntity(post);
        Mono<PostEntity> createdPostEntity = postRepository.save(postEntityToCreate);
        Mono<Post> createdPost =
                createdPostEntity.map(p -> postDomainMapper.mapPostEntityToPost(p));
        return createdPost;
    }
}
