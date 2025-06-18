package com.reservationapi.infra.common.exception;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ErrorResponse {

    private Integer status;
    private List<ErrorDetailResponse> errorDetails;
    private String cause;
    private Meta meta;
}
