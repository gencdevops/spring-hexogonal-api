package com.trendyol.reservationapi.infra.common.exception;

import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import com.trendyol.reservationapi.domain.common.exception.ValidationDomainException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class EnumExceptionHandler {

    private final GlobalExceptionHandler globalExceptionHandler;

    public EnumExceptionHandler(GlobalExceptionHandler globalExceptionHandler) {
        this.globalExceptionHandler = globalExceptionHandler;
    }

    @ExceptionHandler(ValueInstantiationException.class)
    public ResponseEntity<ErrorResponse> handleValueInstantiationException(HttpServletRequest request) {
        ValidationDomainException validationException = new ValidationDomainException(
            "validation.invalid-enum-value.error"
        );

        return globalExceptionHandler.handleBadRequestException(validationException, request);
    }

    @Getter
    @AllArgsConstructor
    @RequiredArgsConstructor
    public static class BaseSystemException extends RuntimeException {

        private final String message;
        private final HttpStatus status;
        private String[] args = ArrayUtils.EMPTY_STRING_ARRAY;
    }

    public static class NotFoundException extends BaseSystemException {

        public NotFoundException(String message) {
            super(message, HttpStatus.NOT_FOUND);
        }

        public NotFoundException(String message, String... args) {
            super(message, HttpStatus.NOT_FOUND, args);
        }
    }
}
