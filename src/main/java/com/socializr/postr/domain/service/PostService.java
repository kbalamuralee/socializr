package com.socializr.postr.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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

    public Flux<Post> fetchAllRecentPosts(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Flux<PostEntity> postEntities =
                postRepository.findAllByOrderByCreationTimestampDesc(pageable);
        Flux<Post> posts = postEntities.map(postDomainMapper::mapPostEntityToPost);
        return posts;
    }
}
