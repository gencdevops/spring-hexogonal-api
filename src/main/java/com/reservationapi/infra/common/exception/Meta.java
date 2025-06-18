package com.reservationapi.infra.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Meta {

    private String requestUri;
    private String requestMethod;
    private String instant;
    private String correlationId;
}
