package com.notion.service.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ReviewStatisticsResponseDto {
    private String productId;
    public int numberReviews;
    public BigDecimal averageReviews;
}
