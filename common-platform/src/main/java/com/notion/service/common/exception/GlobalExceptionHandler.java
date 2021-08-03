package com.notion.service.common.exception;

import com.notion.service.common.dto.response.Response;
import com.notion.service.common.enums.ErrorCode;
import com.notion.service.common.enums.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import  org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException exception, WebRequest request) {
        Map<String, String> dataMap = new HashMap<>();
        exception.getConstraintViolations().stream().
                forEach(violation -> dataMap.put(
                        violation.getPropertyPath().toString(), violation.getMessage()));
        return getExceptionResponseEntity(HttpStatus.BAD_REQUEST, request, dataMap);
    }

    @ExceptionHandler({InvalidRequestException.class})
    public ResponseEntity<Object> handleInvalidRequestException(InvalidRequestException ex, WebRequest request) {
        log.error("handleForbiddenException  : " + ex.getMessage());
        return getExceptionResponseEntity(HttpStatus.BAD_REQUEST, request, ex.getMessage(), ErrorCode.FIELD_VALIDATION.getCode());
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<Object> handleForbiddenException(ForbiddenException ex, WebRequest request) {
        log.error("handleForbiddenException  : " + ex.getMessage());
        return getExceptionResponseEntity(HttpStatus.FORBIDDEN, request, ex.getMessage(), ErrorCode.FORBIDDEN.getCode());
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        log.error("handleResourceNotFoundException  : " + ex.getMessage());
        return getExceptionResponseEntity(HttpStatus.NOT_FOUND, request, ex.getMessage(), ErrorCode.NOT_FOUND.getCode());
    }

   @ExceptionHandler({ConflictException.class})
    public ResponseEntity<Object> handleConflictException(ConflictException ex, WebRequest request) {
        log.error("handleConflictException  : " + ex.getMessage());
        return getExceptionResponseEntity(HttpStatus.CONFLICT, request, ex.getMessage(), ErrorCode.CONFLICT.getCode());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<Object> handleUnauthorizedExceptionException(UnauthorizedException ex, WebRequest request) {
        log.error("handleUnauthorizedExceptionException  : " + ex.getMessage());
        return getExceptionResponseEntity(HttpStatus.UNAUTHORIZED, request, ex.getMessage(), ErrorCode.UNAUTHORIZED.getCode());
    }



    @org.springframework.web.bind.annotation.ExceptionHandler(GeneralException.class)
    protected ResponseEntity onGeneralException(GeneralException ge) {
        log.error("handle general exception : " +  ge.getMessage());
        return new ResponseEntity<Response>(Response.builder()
                .status(ge.getResponseStatus())
                .errorCode(ge.getCode())
                .message(ge.getMessage())
                .build(), ge.getHttpStatus());
    }

    private ResponseEntity<Object> getExceptionResponseEntity(final HttpStatus status, WebRequest request, Map<String, String> errors) {
        final String path = request.getDescription(false);
        return new ResponseEntity<>(Response.builder()
                .status(ResponseStatus.ERROR)
                .message(ErrorCode.FIELD_VALIDATION.getMessage())
                .errorCode(ErrorCode.FIELD_VALIDATION.getCode())
                .data(errors)
                .build(), status);
    }

    private ResponseEntity<Object> getExceptionResponseEntity(final HttpStatus status, WebRequest request, String message, String errorCode) {
        return new ResponseEntity<>(Response.builder()
                .status(ResponseStatus.ERROR)
                .message(message)
                .errorCode(errorCode)
                .build(), status);
    }
}

