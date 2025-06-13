package com.trendyol.reservationapi.domain.blockeddate.exception;

import com.trendyol.reservationapi.domain.common.exception.ValidationDomainException;

public class BlockedDateAlreadyExistException extends ValidationDomainException {

    private static final String ERROR_CODE = "blocked-date-already-exists.error";

    public BlockedDateAlreadyExistException() {
        super(ERROR_CODE);
    }
}
