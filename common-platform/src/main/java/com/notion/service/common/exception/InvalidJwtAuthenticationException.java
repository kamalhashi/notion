package com.notion.service.common.exception;

public class InvalidJwtAuthenticationException extends UnauthorizedException {
    /**
     *
     */
    private static final long serialVersionUID = -761503632186596342L;

    public InvalidJwtAuthenticationException(String e) {
        super(e);
    }
}