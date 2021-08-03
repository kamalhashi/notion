package com.notion.service.common.exception;


import com.notion.service.common.enums.ErrorCode;
import com.notion.service.common.enums.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class GeneralException extends RuntimeException {
    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
    private final ResponseStatus responseStatus;

    public GeneralException(ErrorCode errorCode){
        code = errorCode.getCode();
        message = errorCode.getMessage();
        httpStatus = errorCode.getHttpStatus();
        responseStatus = errorCode.getResponseStatus();
    }

}