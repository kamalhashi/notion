package com.notion.service.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecordNotFoundException extends RuntimeException {
    private final String errorMessage;
}