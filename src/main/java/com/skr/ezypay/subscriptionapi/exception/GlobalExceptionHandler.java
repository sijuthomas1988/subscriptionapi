package com.skr.ezypay.subscriptionapi.exception;

import com.skr.ezypay.subscriptionapi.model.ErrorDetails;
import com.skr.ezypay.subscriptionapi.model.SubscriptionErrorCode;
import com.skr.ezypay.subscriptionapi.model.SubscriptionStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * Class to handle exceptions gracefully and construct error model
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handler used when {@link ServiceException} is  thrown
     *
     * @param e          service exception
     * @param webRequest web request interceptor to fetch metadata of request
     * @return {@link ResponseEntity} of type {@link ErrorDetails}
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> serviceExceptionHandler(ServiceException e, WebRequest webRequest) {
        if (e.getCode().equalsIgnoreCase(SubscriptionErrorCode.REPOSITORY_ERROR.getCode())) {
            ErrorDetails errorDetails = new ErrorDetails(new Date(), SubscriptionErrorCode.REPOSITORY_ERROR.getCode(),
                    SubscriptionErrorCode.REPOSITORY_ERROR.getMessage(), e.getStatus());
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_MODIFIED);
        } else if (e.getCode().equalsIgnoreCase(SubscriptionErrorCode.UNABLE_TO_PROCESS_SUBSCRIPTION_ORDER.getCode())) {
            ErrorDetails errorDetails = new ErrorDetails(new Date(), SubscriptionErrorCode.UNABLE_TO_PROCESS_SUBSCRIPTION_ORDER.getCode(),
                    SubscriptionErrorCode.UNABLE_TO_PROCESS_SUBSCRIPTION_ORDER.getMessage(), e.getStatus());
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        } else {
            ErrorDetails errorDetails = new ErrorDetails(new Date(), SubscriptionErrorCode.UNEXPECTED_ERROR.getCode(),
                    SubscriptionErrorCode.UNEXPECTED_ERROR.getMessage(), e.getStatus());
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handler used when {@link Exception} is  thrown
     *
     * @param e          exception
     * @param webRequest web request interceptor to fetch metadata of request
     * @return {@link ResponseEntity} of type {@link ErrorDetails}
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception e, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), SubscriptionErrorCode.INTERNAL_SERVICE_ERROR.getMessage(), SubscriptionErrorCode.INTERNAL_SERVICE_ERROR.getMessage(), SubscriptionStatus.CANCELLED.name());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
