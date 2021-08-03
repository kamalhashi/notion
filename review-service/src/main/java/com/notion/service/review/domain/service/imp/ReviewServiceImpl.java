package com.notion.service.review.domain.service.imp;

import com.notion.service.common.dto.response.ReviewStatisticsResponseDto;
import com.notion.service.review.domain.dto.ReviewRequestDto;
import com.notion.service.review.domain.entity.Review;
import com.notion.service.review.domain.mapper.ReviewMapper;
import com.notion.service.review.domain.repository.ReviewRepository;
import com.notion.service.review.domain.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }


    @Override
    public Flux<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Mono<Review> save(ReviewRequestDto reviewRequest) {
        return reviewRepository.save(reviewMapper.mapToEntity(reviewRequest));
    }

    @Override
    public Flux<Review> findReviewByProductId(String productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Override
    public ReviewStatisticsResponseDto calculateProductReview(List<Review> reviews, String productId) {
        BigDecimal totalScore = reviews.stream().map(Review::getScore).reduce(BigDecimal.ZERO, BigDecimal::add);
        return ReviewStatisticsResponseDto.builder().numberReviews(reviews.size())
                .averageReviews(totalScore.divide(BigDecimal.valueOf(reviews.size()), 2, RoundingMode.HALF_EVEN))
                .productId(productId)
                .build();

    }
}
