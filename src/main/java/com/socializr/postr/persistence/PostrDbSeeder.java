package com.socializr.postr.persistence;

import java.security.SecureRandom;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import com.socializr.postr.persistence.entity.PostEntity;
import com.socializr.postr.persistence.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostrDbSeeder implements ApplicationRunner {
    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        seedDatabase();
    }

    public void seedDatabase() {
        ZonedDateTime now = ZonedDateTime.now();
        SecureRandom generator = new SecureRandom();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        log.info("Starting seeding");
        for (int i = 0; i < 1_000_000; i++) {
            PostEntity post = new PostEntity();
            post.setUserId(UUID.randomUUID());
            post.setContent(randomString(generator, alphabet, 1000));
            post.setCreationTimestamp(now.minusMinutes(i));
            post.setLastUpdateTimestamp(now.minusMinutes(i));
            postRepository.save(post).block();
        }
        log.info("Done seeding");
    }

    private String randomString(SecureRandom generator, String alphabet, int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(alphabet.charAt(generator.nextInt(alphabet.length())));
        }
        return sb.toString();
    }
}
