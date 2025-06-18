package com.reservationapi.domain.blockeddate.exception;

import com.reservationapi.domain.common.exception.ValidationDomainException;

public class BlockedDateInvalidPriorityException extends ValidationDomainException {

    private static final String ERROR_CODE = "blocked-date-invalid-priority.error";

    public BlockedDateInvalidPriorityException() {
        super(ERROR_CODE);
    }
}
