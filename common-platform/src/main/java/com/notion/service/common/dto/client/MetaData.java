package com.notion.service.common.dto.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MetaData {
    private String canonical;
    private String description;
    private String keywords;
    @JsonProperty("page_title")
    private String pageTitle;
    @JsonProperty("site_name")
    private String siteName;
}
