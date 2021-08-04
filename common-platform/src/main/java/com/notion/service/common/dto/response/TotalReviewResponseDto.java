package com.notion.service.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TotalReviewResponseDto {
    private String productId;
    private int numberReviews;
    private BigDecimal averageReviews;
}
