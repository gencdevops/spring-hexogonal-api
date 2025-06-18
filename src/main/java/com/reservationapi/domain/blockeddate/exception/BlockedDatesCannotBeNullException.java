package com.reservationapi.domain.blockeddate.exception;

import com.reservationapi.domain.common.exception.ValidationDomainException;

public class BlockedDatesCannotBeNullException extends ValidationDomainException {

    private static final String ERROR_CODE = "blocked-dates-cannot-be-null.error";

    public BlockedDatesCannotBeNullException() {
        super(ERROR_CODE);
    }
}
