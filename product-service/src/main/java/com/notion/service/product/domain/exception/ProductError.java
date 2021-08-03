package com.notion.service.product.domain.exception;


import com.notion.service.common.enums.ErrorCode;
import com.notion.service.common.enums.ResponseStatus;
import org.springframework.http.HttpStatus;

public class ProductError extends ErrorCode {

    // HttpStatus - Not found
    public static final ProductError PRODUCT_NOT_FOUND = new ProductError(HttpStatus.NOT_FOUND, ServiceCode.REVIEW_SERVICE, "001", "product not found", ResponseStatus.ERROR);
    public static final ProductError ADIDAS_PRODUCT_NOT_FOUND = new ProductError(HttpStatus.NOT_FOUND, ServiceCode.REVIEW_SERVICE, "001", "product id not found in adidas DB", ResponseStatus.ERROR);

    //HttpStatus - BadRequest
    public static final ProductError INVALID_PRODUCT_ID = new ProductError(HttpStatus.BAD_REQUEST, ServiceCode.REVIEW_SERVICE, "002", "invalid product id", ResponseStatus.ERROR);

    // HttpStatus - Forbidden
    public static final ProductError SAVE_PRODUCT_FORBIDDEN = new ProductError(HttpStatus.FORBIDDEN, ServiceCode.REVIEW_SERVICE, "003", "forbidden to save product", ResponseStatus.ERROR);


    protected ProductError(String code, String message, HttpStatus status, ResponseStatus responseStatus) {
        super(code, message, status, responseStatus);
    }

    public ProductError(HttpStatus httpStatus, ServiceCode serviceCode, String codeNumber, String message, ResponseStatus responseStatus) {
        super(httpStatus, serviceCode, codeNumber, message, responseStatus);
    }
}
