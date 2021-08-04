package com.notion.service.product.domain.dto;


import com.notion.service.common.dto.client.AdidasClientResponseDto;
import com.notion.service.common.dto.response.TotalReviewResponseDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductReviewResponseDto {
    private AdidasClientResponseDto adidasResponseDto;
    private TotalReviewResponseDto totalReviewResponseDto;
}
