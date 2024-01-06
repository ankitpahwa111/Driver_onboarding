package com.intuit.uber.onboarding.factory;

import com.intuit.uber.onboarding.model.entity.CustomResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CustomResponseEntityFactory {
    public CustomResponseEntity getSuccessResponse(Object data) {
        return CustomResponseEntity.builder()
                .httpStatus(HttpStatus.OK)
                .data(data)
                .message(HttpStatus.OK.getReasonPhrase())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    public CustomResponseEntity getBadRequestResponse(String errorMessage) {
        return CustomResponseEntity.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .data(null)
                .message(errorMessage)
                .timestamp(System.currentTimeMillis())
                .build();
    }
    public CustomResponseEntity getISEResponse() {
        return CustomResponseEntity.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .data(null)
                .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    public CustomResponseEntity getNotFoundResponse() {
        return CustomResponseEntity.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .data(null)
                .message(HttpStatus.NOT_FOUND.getReasonPhrase())
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
