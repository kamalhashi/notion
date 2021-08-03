package com.notion.service.review.domain.config;

import com.notion.service.review.domain.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class AppRunner implements CommandLineRunner {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public void run(String... args) throws Exception {
       initReviewCollection();
    }

    private void initReviewCollection() {

        // Insert one book
        Review review1 = Review.builder()
                .productId("ZZZZ") //non existing
                .score(BigDecimal.valueOf(5))
                .build();


        Review review2 = Review.builder()
                .productId("BB5476") //non existing
                .score(BigDecimal.valueOf(5))
                .build();

        Review review3 = Review.builder()
                .productId("BB5476")
                .score(BigDecimal.valueOf(4))
                .build();

        reactiveMongoTemplate.insert(Arrays.asList(review1, review2, review3), Review.class).subscribe();
    }
}
