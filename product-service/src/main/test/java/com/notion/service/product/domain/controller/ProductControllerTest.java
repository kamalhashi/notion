package com.notion.service.product.domain.controller;

import com.notion.service.common.client.AdidasClient;
import com.notion.service.common.client.ReviewClient;
import com.notion.service.common.dto.client.AdidasClientResponseDto;
import com.notion.service.common.dto.client.TotalReviewClientResponseDto;
import com.notion.service.common.dto.response.Response;
import com.notion.service.common.dto.response.TotalReviewResponseDto;
import com.notion.service.product.domain.config.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Config.class})
@WebFluxTest(controllers = ProductController.class, excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class, ReactiveOAuth2ResourceServerAutoConfiguration.class, ReactiveOAuth2ClientAutoConfiguration.class})
class ProductControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Mock
    AdidasClient adidasClient;

    @Mock
    ReviewClient reviewClient;


    @Test
    void findProductAndReviewByProductId() {

        Mockito
                .when(adidasClient.getProductByProductId("1234"))
                .thenReturn(createAdidasClientResponseDummy());

        Mockito
                .when(reviewClient.getProductByProductId(Mockito.anyString()))
                .thenReturn(createTotalReviewResponseDummy());

        webTestClient.get()
                .uri("/v1/products/1234")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();


    }

    public Mono<AdidasClientResponseDto> createAdidasClientResponseDummy() {
        AdidasClientResponseDto adidasClientResponseDto = new AdidasClientResponseDto();
        adidasClientResponseDto.setProductId("1234");
        return Mono.just(adidasClientResponseDto);
    }

    public Mono<TotalReviewClientResponseDto> createTotalReviewResponseDummy() {
        TotalReviewClientResponseDto responseDto = new TotalReviewClientResponseDto();

        TotalReviewResponseDto responseDto1 = TotalReviewResponseDto.builder()
                .numberReviews(10)
                .averageReviews(BigDecimal.valueOf(10))
                .productId("1234")
                .build();

        responseDto.setData(responseDto1);
        return Mono.just(responseDto);
    }

}