package com.notion.service.product.domain.controller;

import com.notion.service.common.client.AdidasClient;
import com.notion.service.common.client.ReviewClient;
import com.notion.service.common.dto.client.AdidasClientResponseDto;
import com.notion.service.common.dto.client.ReviewStatisticsClientResponseDto;
import com.notion.service.common.dto.response.Response;
import com.notion.service.common.enums.ResponseStatus;
import com.notion.service.product.domain.dto.ProductReviewResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Collection;


@RestController
@RequestMapping("/v1/products")
@Slf4j
public class ProductController {

    private final AdidasClient adidasClient;
    private final ReviewClient reviewClient;

    public ProductController(AdidasClient adidasClient, ReviewClient reviewClient) {
        this.adidasClient = adidasClient;
        this.reviewClient = reviewClient;
    }

    @PreAuthorize("hasRole('ROLE_WRITE')")
    @GetMapping("/current-user")
    public Mono<Collection<? extends GrantedAuthority>> hello(Mono<Authentication> authentication) {
        return authentication
                .map(Authentication::getAuthorities);
    }


    @GetMapping("{productId}")
    public Mono<?> findProductAndReviewByProductId(@PathVariable("productId") String productId) {
        return adidasClient.getProductByProductId(productId)
                .onErrorResume( throwable ->   Mono.just(new AdidasClientResponseDto()))
                .zipWith(reviewClient.getProductByProductId(productId)
                        .onErrorResume( throwable ->   Mono.just(new ReviewStatisticsClientResponseDto())))
                        .map(tuples -> {
                    return ProductReviewResponseDto
                            .builder()
                            .adidasResponseDto(tuples.getT1())
                            .reviewStatisticsResponseDto(tuples.getT2().getData())
                           .build();
                })
                .map(review -> ResponseEntity.ok(Response.<ProductReviewResponseDto>builder()
                        .data(review)
                        .status(ResponseStatus.SUCCESS)
                        .build()));
    }
}
