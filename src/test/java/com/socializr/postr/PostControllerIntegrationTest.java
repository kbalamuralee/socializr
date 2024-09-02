package com.socializr.postr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.time.Duration;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.socializr.postr.api.model.PostCreationResponse;
import com.socializr.postr.api.model.PostResponse;
import com.socializr.postr.api.model.PostUpdateRequest;
import com.socializr.postr.domain.model.Post;
import lombok.Getter;
import lombok.Setter;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        webTestClient = webTestClient.mutate().responseTimeout(Duration.ofSeconds(15)).build();
    }

    @Test
    public void testPostLifeCycle() {
        UUID testUserId = UUID.randomUUID();
        String testPostContent = "THIS IS A TEST";
        String updatedTestPostContent = "THIS IS ALSO A TEST";
        Holder<UUID> postIdHolder = new Holder<>();
        Post post = new Post();
        post.setUserId(testUserId);
        post.setContent(testPostContent);
        webTestClient.post().uri("/posts").bodyValue(post).exchange().expectStatus().isOk()
                .expectBody(PostCreationResponse.class).value(response -> {
                    assertNotNull(response.getId());
                    postIdHolder.setItem(response.getId());
                });
        UUID postId = postIdHolder.getItem();
        webTestClient.get().uri("/posts/{id}", postId).exchange().expectStatus().isOk()
                .expectBody(PostResponse.class).value(response -> {
                    assertEquals(postId, response.getId());
                    assertEquals(testUserId, response.getUserId());
                    assertEquals(testPostContent, response.getContent());
                });
        PostUpdateRequest postUpdateRequest = new PostUpdateRequest();
        postUpdateRequest.setContent(updatedTestPostContent);
        webTestClient.put().uri("/posts/{id}", postId).bodyValue(postUpdateRequest).exchange()
                .expectStatus().isOk().expectBody(PostResponse.class).value(response -> {
                    assertEquals(postId, response.getId());
                    assertEquals(testUserId, response.getUserId());
                    assertEquals(updatedTestPostContent, response.getContent());
                });
        int pageSize = 1;
        int expectedPostPageNumber = 0;
        webTestClient.get()
                .uri("/posts?pageNumber={n}&pageSize={k}", expectedPostPageNumber, pageSize)
                .exchange().expectStatus().isOk().expectBody(PostResponse.class).value(response -> {
                    assertEquals(postId, response.getId());
                    assertEquals(testUserId, response.getUserId());
                    assertEquals(updatedTestPostContent, response.getContent());
                });
        webTestClient.delete().uri("/posts/{id}", postId).exchange().expectStatus().isOk();
        webTestClient.get().uri("/posts/{id}", postId).exchange().expectStatus().isNotFound();
    }

    @Getter
    @Setter
    public static class Holder<T> {
        private T item;
    }
}
