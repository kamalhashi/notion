package com.notion.service.review.domain.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ReviewResponseDto {
    private String id;
    private String productId;
    private BigDecimal score;
    private LocalDateTime creationDate;
    private LocalDateTime updatedAt;
}
