package com.reservationapi.domain.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class BaseDomainException extends RuntimeException {

    private final String key;
    private String[] args = ArrayUtils.EMPTY_STRING_ARRAY;
}
