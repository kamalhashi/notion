package com.notion.service.common.client;

import com.notion.service.common.dto.client.AdidasClientResponseDto;
import com.notion.service.common.enums.ErrorCode;
import com.notion.service.common.enums.ResponseStatus;
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
public class AdidasClient {
    private final WebClient client;
    private final String baseUrl;

    public AdidasClient(WebClient.Builder builder, @Value("${app.services.adidasservice.baseurl}") String baseUrl) {
        this.client = builder.baseUrl(baseUrl).build();
        this.baseUrl = baseUrl;
    }

    public Mono<AdidasClientResponseDto> getProductByProductId(String productId) {
        return this.client.get()
                .uri(uriBuilder -> uriBuilder.path("/api/products/")
                        .path(productId).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> {
                    log.error("error in fetching one of the productId: {} from adidas server httpsCode:{}", productId, clientResponse.rawStatusCode());
                    new ErrorCode(clientResponse.statusCode(), ErrorCode.ServiceCode.COMMON, "xxxClient" ,"Adidas Server returning error for productId" + productId, ResponseStatus.ERROR);
                    return Mono.error(new GeneralException(ErrorCode.NOT_FOUND));
                })
                .bodyToMono(AdidasClientResponseDto.class);
    }


}
