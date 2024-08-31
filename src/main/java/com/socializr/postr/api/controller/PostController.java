package com.socializr.postr.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.socializr.postr.api.mapper.PostApiMapper;
import com.socializr.postr.api.model.PostCreationRequest;
import com.socializr.postr.api.model.PostCreationResponse;
import com.socializr.postr.domain.model.Post;
import com.socializr.postr.domain.service.PostService;
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
                .map(p -> postApiMapper.mapPostToPostCreationResponse(p));
    }
}
