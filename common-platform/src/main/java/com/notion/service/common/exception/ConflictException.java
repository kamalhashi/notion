package com.notion.service.common.exception;

import lombok.Data;

@Data
public class ConflictException extends RuntimeException {
    public ConflictException(String errorMessage) {
        super(errorMessage);
    }
}
