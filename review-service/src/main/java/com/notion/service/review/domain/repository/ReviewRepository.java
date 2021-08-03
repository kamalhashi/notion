package com.notion.service.review.domain.repository;

import com.notion.service.review.domain.entity.Review;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ReviewRepository extends ReactiveMongoRepository<Review, String> {
    public Flux<Review> findByProductId(String productId);
}
