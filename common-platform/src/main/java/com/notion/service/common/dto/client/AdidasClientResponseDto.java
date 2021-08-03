package com.notion.service.common.dto.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdidasClientResponseDto {
    @JsonProperty("id")
    private String productId;
    @JsonProperty("model_number")
    private String modelNumber;
    private String name;
    @JsonProperty("meta_data")
    private MetaData metaData;
}