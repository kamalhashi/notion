package com.notion.service.review.domain.controller;

import com.notion.service.common.dto.response.Response;
import com.notion.service.common.dto.response.ReviewStatisticsResponseDto;
import com.notion.service.common.enums.ResponseStatus;
import com.notion.service.common.exception.GeneralException;
import com.notion.service.review.domain.dto.ReviewRequestDto;
import com.notion.service.review.domain.dto.ReviewResponseDto;
import com.notion.service.review.domain.exception.ReviewError;
import com.notion.service.review.domain.mapper.ReviewMapper;
import com.notion.service.review.domain.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/reviews")
@Slf4j
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;



    @PreAuthorize("hasRole('ROLE_WRITE')")
    @PostMapping
    public Mono<ResponseEntity<Response<ReviewResponseDto>>> saveReview(@Valid @RequestBody ReviewRequestDto reviewRequest) {
        return reviewService.save(reviewRequest)
                .map(review -> new ResponseEntity<>(Response.<ReviewResponseDto>builder()
                        .data(reviewMapper.mapToDto(review))
                        .status(ResponseStatus.SUCCESS)
                        .build(), HttpStatus.CREATED));
    }

    @GetMapping
    public Mono<ResponseEntity<Response>> findAllReviews() {
        return reviewService.findAll()
                .switchIfEmpty(Mono.error(new GeneralException(ReviewError.PRODUCT_NOT_FOUND)))
                .collectList()
                .map(review -> ResponseEntity.ok(Response.builder()
                        .status(ResponseStatus.SUCCESS)
                        .data(reviewMapper.mapList(review, ReviewResponseDto.class))
                        .status(ResponseStatus.SUCCESS)
                        .build()));
    }

    @GetMapping("products/{productId}")
    public Mono<ResponseEntity<Response<ReviewStatisticsResponseDto>>> findReviewByProductId(@PathVariable("productId") String productId) {
        return reviewService.findReviewByProductId(productId)
              .switchIfEmpty(Mono.error(new GeneralException(ReviewError.PRODUCT_NOT_FOUND)))
               .collectList()
                .map(reviews -> reviewService.calculateProductReview(reviews, productId))
                .map(review -> ResponseEntity.ok(Response.<ReviewStatisticsResponseDto>builder()
                        .data(review)
                        .status(ResponseStatus.SUCCESS)
                        .build()));
    }
}
