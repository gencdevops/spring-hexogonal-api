package com.trendyol.reservationapi.infra.common.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends EnumExceptionHandler.BaseSystemException {

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public BadRequestException(String message, String... args) {
        super(message, HttpStatus.BAD_REQUEST, args);
    }
}
