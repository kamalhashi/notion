package com.notion.service.common.dto.client;

import com.notion.service.common.dto.response.ReviewStatisticsResponseDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReviewStatisticsClientResponseDto {
    private ReviewStatisticsResponseDto data;
}
