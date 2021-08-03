package com.notion.service.review.domain.service.imp;

import com.notion.service.common.dto.response.Response;
import com.notion.service.common.dto.response.ReviewStatisticsResponseDto;
import com.notion.service.review.domain.config.Config;
import com.notion.service.review.domain.dto.ReviewRequestDto;
import com.notion.service.review.domain.entity.Review;
import com.notion.service.review.domain.mapper.ReviewMapper;
import com.notion.service.review.domain.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Config.class})
public class ReviewServiceImplTest {

    @Autowired
    private ReviewServiceImpl reviewService;

    @InjectMocks
    private ReviewMapper reviewMapper;

    @MockBean
    private ReviewRepository reviewRepository;


    @Test
    public void findAllShouldReturnTwoReviewSuccessfully() {

        Mockito
                .when(reviewRepository.findAll())
                .thenReturn(createReviews());

        Flux<Review> reviews = reviewService.findAll();

        StepVerifier
                .create(reviews)
                .expectNextMatches(review -> review.getId().equals("123"))
                .expectNextCount(1)
                .expectComplete()
                .verify();
        Mockito.verify(reviewRepository, times(1)).findAll();
    }

    @Test
    public void saveReviewSuccessfully() {
        Mockito
                .when(reviewRepository.save(any()))
                .thenReturn(createReview());

        Mono<Review> review = reviewService.save(createReviewRequestDto());

        StepVerifier
                .create(review)
                .expectNextMatches(r -> r.getProductId().equals("BB5476"))
                .expectComplete()
                .verify();

        Mockito.verify(reviewRepository, times(1)).save(any());

    }

    @Test
    public void findReviewByProductIdShouldReturnOneReviewSuccessfully() {

        Mockito
                .when(reviewRepository.findByProductId(any()))
                .thenReturn(createReviews());

        Flux<Review> reviews = reviewService.findReviewByProductId(any());

        StepVerifier
                .create(reviews)
                .expectNextMatches(review -> review.getId().equals("123"))
                .expectNextCount(1)
                .expectComplete()
                .verify();

        Mockito.verify(reviewRepository, times(1)).findByProductId(any());
    }

    private Flux<Review> createReviews() {
        Review review = new Review();
        review.setId("123");
        review.setProductId("BB5476");
        review.setScore(BigDecimal.valueOf(2));

        Review review2 = new Review();
        review2.setId("1234");
        review2.setProductId("BB5476");
        review2.setScore(BigDecimal.valueOf(2));
        return Flux.just(review, review2);
    }


    private Mono<Review> createReview() {
        Review review = new Review();
        review.setId("12345");
        review.setProductId("BB5476");
        review.setScore(BigDecimal.valueOf(2));

        return Mono.just(review);
    }

    private ReviewRequestDto createReviewRequestDto() {
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto();
        reviewRequestDto.setProductId("BB5476");
        reviewRequestDto.setScore(BigDecimal.valueOf(2));

        return reviewRequestDto;
    }
}