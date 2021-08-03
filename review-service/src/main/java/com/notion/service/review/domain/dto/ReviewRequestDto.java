package com.notion.service.review.domain.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ReviewRequestDto {
    @NotBlank
    String productId;
    @NotNull
    @Max(10)
    BigDecimal score;
}
