package com.notion.service.review.domain.exception;


import com.notion.service.common.enums.ErrorCode;
import com.notion.service.common.enums.ResponseStatus;
import org.springframework.http.HttpStatus;

public class ReviewError extends ErrorCode {

    // HttpStatus - Not found
    public static final ReviewError PRODUCT_NOT_FOUND = new ReviewError(HttpStatus.NOT_FOUND, ServiceCode.REVIEW_SERVICE, "001", "product not found", ResponseStatus.ERROR);
    public static final ReviewError ADIDAS_CLIENT_PRODUCT_NOT_FOUND = new ReviewError(HttpStatus.NOT_FOUND, ServiceCode.REVIEW_SERVICE, "001", "product id not found in adidas DB", ResponseStatus.ERROR);

    //HttpStatus - BadRequest
    public static final ReviewError INVALID_PRODUCT_ID = new ReviewError(HttpStatus.BAD_REQUEST, ServiceCode.REVIEW_SERVICE, "002", "invalid product id", ResponseStatus.ERROR);

    // HttpStatus - Forbidden
    public static final ReviewError SAVE_PRODUCT_FORBIDDEN = new ReviewError(HttpStatus.FORBIDDEN, ServiceCode.REVIEW_SERVICE, "003", "forbidden to save product", ResponseStatus.ERROR);


    protected ReviewError(String code, String message, HttpStatus status, ResponseStatus responseStatus) {
        super(code, message, status, responseStatus);
    }

    public ReviewError(HttpStatus httpStatus, ServiceCode serviceCode, String codeNumber, String message, ResponseStatus responseStatus) {
        super(httpStatus, serviceCode, codeNumber, message, responseStatus);
    }
}
