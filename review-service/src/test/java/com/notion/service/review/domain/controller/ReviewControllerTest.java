package com.notion.service.review.domain.controller;


import com.notion.service.common.dto.response.Response;
import com.notion.service.common.dto.response.TotalReviewResponseDto;
import com.notion.service.review.domain.config.Config;
import com.notion.service.review.domain.dto.ReviewRequestDto;
import com.notion.service.review.domain.entity.Review;
import com.notion.service.review.domain.mapper.ReviewMapper;
import com.notion.service.review.domain.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.reactive.ReactiveOAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.util.List;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Config.class})
@WebFluxTest(controllers = ReviewController.class, excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class, ReactiveOAuth2ResourceServerAutoConfiguration.class, ReactiveOAuth2ClientAutoConfiguration.class})
public class ReviewControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @InjectMocks
    private ReviewMapper reviewMapper;


    @MockBean
    private ReviewService reviewService;

    @Test
    public void saveReviewShouldSaveSuccessFully() {

        Mockito
                .when(reviewService.save(Mockito.any()))
                .thenReturn(createReview());

        webTestClient.post()
                .uri("/v1/reviews")
                .accept(MediaType.APPLICATION_JSON)
                .body(createReviewRequestDto(), ReviewRequestDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Response.class);


       Mockito.verify(reviewService, times(1)).save(Mockito.any());


    }

    @Test
    public void findAllReviewsShouldReturnTwoReviews() {

        Mockito
                .when(reviewService.findAll())
                .thenReturn(createReviews());

        webTestClient.get()
                .uri("/v1/reviews")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Response.class)
                .value(response -> ((List<TotalReviewResponseDto>) response.getData()).size(), equalTo(2));

        Mockito.verify(reviewService, times(1)).findAll();

    }


    private Flux<Review> createReviews(){
        Review review = new Review();
        review.setId("123");
        review.setProductId("BB5476");
        review.setScore(BigDecimal.valueOf(2));

        Review review2 = new Review();
        review2.setId("123");
        review2.setProductId("BB5476");
        review2.setScore(BigDecimal.valueOf(2));
        return Flux.just(review, review2);
    }


    private Mono<Review> createReview(){
        Review review = new Review();
        review.setId("123");
        review.setProductId("BB5476");
        review.setScore(BigDecimal.valueOf(2));

        return Mono.just(review);
    }

    private Mono<ReviewRequestDto> createReviewRequestDto(){
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto();
        reviewRequestDto.setProductId("BB5476");
        reviewRequestDto.setScore(BigDecimal.valueOf(2));

        return Mono.just(reviewRequestDto);
    }
}