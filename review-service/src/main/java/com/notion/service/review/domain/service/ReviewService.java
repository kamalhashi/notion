package com.notion.service.review.domain.service;

import com.notion.service.common.dto.response.ReviewStatisticsResponseDto;
import com.notion.service.review.domain.dto.ReviewRequestDto;
import com.notion.service.review.domain.entity.Review;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ReviewService {
    public Flux<Review> findAll();

    public Mono<Review> save(ReviewRequestDto reviewRequest);

    public Flux<Review> findReviewByProductId(String productId);

    ReviewStatisticsResponseDto calculateProductReview(List<Review> reviews, String productId);
}
