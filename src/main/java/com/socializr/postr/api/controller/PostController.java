package com.socializr.postr.api.controller;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.socializr.postr.api.mapper.PostApiMapper;
import com.socializr.postr.api.model.PostCreationRequest;
import com.socializr.postr.api.model.PostCreationResponse;
import com.socializr.postr.api.model.PostResponse;
import com.socializr.postr.api.model.PostUpdateRequest;
import com.socializr.postr.domain.model.Post;
import com.socializr.postr.domain.service.PostService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostApiMapper postApiMapper;
    @Autowired
    private PostService postService;

    @PostMapping
    public Mono<PostCreationResponse> createPost(
            @RequestBody PostCreationRequest postCreationRequest) {
        Post postToCreate = postApiMapper.mapPostCreationRequestToPost(postCreationRequest);
        return postService.createPost(postToCreate)
                .map(postApiMapper::mapPostToPostCreationResponse);
    }

    @GetMapping("/{id}")
    public Mono<PostResponse> fetchPost(@PathVariable UUID id) {
        Mono<Post> fetchedPost = postService.fetchPost(id);
        return fetchedPost.map(postApiMapper::mapPostToPostResponse);
    }

    @PutMapping("/{id}")
    public Mono<PostResponse> updatePost(@PathVariable UUID id,
            @RequestBody PostUpdateRequest postUpdateRequest) {
        Post postDelta = postApiMapper.mapPostUpdateRequestToPost(id, postUpdateRequest);
        Mono<Post> updatedPost = postService.updatePost(postDelta);
        return updatedPost.map(postApiMapper::mapPostToPostResponse);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deletePost(@PathVariable UUID id) {
        return postService.deletePost(id);
    }

    @GetMapping
    public Flux<PostResponse> fetchLatestPosts(@RequestParam int pageNumber,
            @RequestParam int pageSize) {
        Flux<Post> posts = postService.fetchAllRecentPosts(pageNumber, pageSize);
        return posts.map(postApiMapper::mapPostToPostResponse);
    }

    @GetMapping(path = "/stream", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<PostResponse> streamLatestPosts(@RequestParam int pageNumber,
            @RequestParam int pageSize) {
        return fetchLatestPosts(pageNumber, pageSize);
    }
}
