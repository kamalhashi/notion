package com.notion.service.common.enums;

import org.springframework.http.HttpStatus;

public class ErrorCode {
    // HttpStatus - BAD_REQUEST
    public static ErrorCode FIELD_VIOLATION = new ErrorCode(HttpStatus.BAD_REQUEST, ServiceCode.COMMON, "001" ,"Field Violation", ResponseStatus.ERROR);
    public static ErrorCode FIELD_VALIDATION = new ErrorCode(HttpStatus.BAD_REQUEST, ServiceCode.COMMON, "002" ,"Field Validation", ResponseStatus.ERROR);
    public static ErrorCode REQUEST_NOT_PARSED = new ErrorCode(HttpStatus.BAD_REQUEST, ServiceCode.COMMON, "003" ,"Request Body Not parsed", ResponseStatus.ERROR);
    public static ErrorCode FEIGN_EXCEPTION = new ErrorCode(HttpStatus.BAD_REQUEST, ServiceCode.COMMON, "004" ,"feign_client_error", ResponseStatus.ERROR);
    public static ErrorCode PAYMENT_SERVICE_DATABASE_CONSTRAINT_FAILED = new ErrorCode("409.030.002","Database Constraint Failed", HttpStatus.BAD_REQUEST, ResponseStatus.ERROR);
    public static ErrorCode PAYMENT_SERVICE_INTERNAL_SERVER_ERROR = new ErrorCode("500.030.003","Internal server error", HttpStatus.BAD_REQUEST, ResponseStatus.ERROR);
    public static ErrorCode PAYMENT_SERVICE_CARD_NUMBER_NOT_FOUND = new ErrorCode("400.030.004","Card Number Not Found", HttpStatus.BAD_REQUEST, ResponseStatus.ERROR);

    // HttpStatus - UNAUTHORIZED
    public static ErrorCode UNAUTHORIZED = new ErrorCode(HttpStatus.UNAUTHORIZED, ServiceCode.COMMON, "001", "Unauthorized", ResponseStatus.ERROR);
    public static ErrorCode UNAUTHORIZED_ACCESS = new ErrorCode(HttpStatus.UNAUTHORIZED, ServiceCode.COMMON, "002","Unauthorised token", ResponseStatus.ERROR);

    // HttpStatus - NOT_FOUND
    public static ErrorCode NOT_FOUND = new ErrorCode(HttpStatus.NOT_FOUND, ServiceCode.COMMON, "001","Resource not found", ResponseStatus.ERROR);
    public static ErrorCode RESOURCE_NOT_FOUND = new ErrorCode(HttpStatus.NOT_FOUND, ServiceCode.COMMON, "002","Resource not found", ResponseStatus.ERROR);
    public static ErrorCode ITEM_NOT_FOUND = new ErrorCode(HttpStatus.NOT_FOUND, ServiceCode.COMMON, "003", "Item not found in database", ResponseStatus.ERROR);

    // HttpStatus - Conflict
    public static ErrorCode CONFLICT = new ErrorCode(HttpStatus.CONFLICT, ServiceCode.COMMON, "001","conflict", ResponseStatus.ERROR);
    public static ErrorCode DATABASE_CONSTRAINT_FAILED = new ErrorCode(HttpStatus.CONFLICT, ServiceCode.COMMON, "002","Database Constraint Failed", ResponseStatus.ERROR);


    // HttpStatus - FORBIDDEN
    public static ErrorCode FORBIDDEN = new ErrorCode(HttpStatus.FORBIDDEN, ServiceCode.COMMON, "001" ,"Resource not found", ResponseStatus.ERROR);
    public static ErrorCode INVALID_ROLE = new ErrorCode(HttpStatus.FORBIDDEN, ServiceCode.COMMON, "002" ,"Invalid Role", ResponseStatus.ERROR);

    // HttpStatus - Server Error
    public static ErrorCode INTERNAL_SERVER_ERROR = new ErrorCode(HttpStatus.INTERNAL_SERVER_ERROR, ServiceCode.COMMON, "001","Internal server error", ResponseStatus.ERROR);

    private String errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;
    private ResponseStatus responseStatus;

    public ErrorCode(String code, String message, HttpStatus httpStatus, ResponseStatus responseStatus){
        this.errorCode = code;
        this.errorMessage = message;
        this.httpStatus = httpStatus;
        this.responseStatus = responseStatus;
    }

    public ErrorCode(HttpStatus httpStatus, ServiceCode serviceCode, String codeNumber, String message, ResponseStatus responseStatus) {
        this.errorCode = formatCode(httpStatus, serviceCode, codeNumber);
        this.errorMessage = message;
        this.httpStatus = httpStatus;
        this.responseStatus = responseStatus;
    }

    public String getCode() { return this.errorCode; }

    public String getMessage() { return this.errorMessage; }

    public HttpStatus getHttpStatus() { return  this.httpStatus; }

    public ResponseStatus getResponseStatus() { return this.responseStatus; }

    private static String formatCode(HttpStatus httpStatus, ServiceCode serviceCode, String codeNumber) {
        return String.format("%s.%s.%s", httpStatus.value(), serviceCode.getServiceCode(), codeNumber);
    }

    public enum ServiceCode {
        COMMON("001"),
        PRODUCT_SERVICE("002"),
        REVIEW_SERVICE("003");

        private final String serviceCode;

        ServiceCode(String serviceCode) {
            this.serviceCode = serviceCode;
        }

        public String getServiceCode() {
            return this.serviceCode;
        }
    }
}
