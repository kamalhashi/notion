package com.notion.service.common.exception;

import lombok.Data;

@Data
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String errorMessage) {
        super(errorMessage);
    }
}
