package com.trendyol.reservationapi.domain.common.exception;

public class ValidationDomainException extends BaseDomainException {

    public ValidationDomainException(String message) {
        super(message);
    }

    public ValidationDomainException(String message, String... args) {
        super(message, args);
    }
}
