package com.notion.service.common.client;

import com.notion.service.common.dto.client.ReviewStatisticsClientResponseDto;
import com.notion.service.common.enums.ErrorCode;
import com.notion.service.common.exception.GeneralException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ReviewClient {
    private final WebClient client;
    private final String baseUrl;

    public ReviewClient(WebClient.Builder builder, @Value("${app.services.reviewservice.baseurl}") String baseUrl) {
        this.client = builder.baseUrl(baseUrl).build();
        this.baseUrl = baseUrl;
    }

    public Mono<ReviewStatisticsClientResponseDto> getProductByProductId(String productId) {
        return this.client.get()
                .uri(uriBuilder -> uriBuilder.path("/v1/reviews/products/")
                        .path(productId).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> {
                    log.error("error in fetching one of the productId: {} from adidas server httpsCode:{}", productId, clientResponse.rawStatusCode());
                    return Mono.error(new GeneralException(ErrorCode.NOT_FOUND));
                })
                .bodyToMono(ReviewStatisticsClientResponseDto.class);
    }


}
