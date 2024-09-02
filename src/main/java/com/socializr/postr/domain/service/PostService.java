package com.socializr.postr.domain.service;

import java.time.ZonedDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.socializr.postr.domain.mapper.PostDomainMapper;
import com.socializr.postr.domain.model.Post;
import com.socializr.postr.persistence.entity.PostEntity;
import com.socializr.postr.persistence.repository.PostRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PostService {
    @Autowired
    private PostDomainMapper postDomainMapper;
    @Autowired
    private PostRepository postRepository;

    public Mono<Post> createPost(Post post) {
        PostEntity postEntityToCreate = postDomainMapper.mapPostToPostEntity(post);
        Mono<PostEntity> createdPostEntity = postRepository.save(postEntityToCreate);
        Mono<Post> createdPost = createdPostEntity.map(postDomainMapper::mapPostEntityToPost);
        return createdPost;
    }

    public Mono<Post> fetchPost(UUID id) {
        Mono<PostEntity> fetchedPostEntity = postRepository.findById(id);
        Mono<Post> post = fetchedPostEntity.map(postDomainMapper::mapPostEntityToPost)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found")));
        return post;
    }

    public Mono<Post> updatePost(Post post) {
        UUID id = post.getId();
        PostEntity postEntityDelta = postDomainMapper.mapPostToPostEntity(post);
        return this.fetchPost(id).<PostEntity>flatMap(existingPost -> {
            existingPost.setContent(postEntityDelta.getContent());
            existingPost.setLastUpdateTimestamp(ZonedDateTime.now());
            PostEntity existingPostEntity = postDomainMapper.mapPostToPostEntity(existingPost);
            return postRepository.save(existingPostEntity);
        }).map(postDomainMapper::mapPostEntityToPost);
    }

    public Mono<Void> deletePost(UUID id) {
        return postRepository.deleteById(id);
    }

    public Flux<Post> fetchAllRecentPosts(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Flux<PostEntity> postEntities =
                postRepository.findAllByOrderByCreationTimestampDesc(pageable);
        Flux<Post> posts = postEntities.map(postDomainMapper::mapPostEntityToPost);
        return posts;
    }
}
