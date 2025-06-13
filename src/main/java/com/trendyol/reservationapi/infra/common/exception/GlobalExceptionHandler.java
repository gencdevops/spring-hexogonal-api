package com.trendyol.reservationapi.infra.common.exception;

import com.trendyol.reservationapi.domain.common.exception.BaseDomainException;
import com.trendyol.reservationapi.domain.common.exception.ValidationDomainException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({ BaseDomainException.class })
    public ResponseEntity<ErrorResponse> handleBusinessException(
        BaseDomainException exception,
        HttpServletRequest request
    ) {
        LOGGER.warn("A business error has occurred, error: {} for request {}", exception, request);
        return buildErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({ ValidationDomainException.class })
    public ResponseEntity<ErrorResponse> handleBadRequestException(
        BaseDomainException exception,
        HttpServletRequest request
    ) {
        LOGGER.warn("A business bad request error has occurred, error: {} for request {}", exception, request);
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException exception,
        HttpServletRequest request
    ) {
        LOGGER.warn("A method argument not valid error has occurred, error: {} for request {}", exception, request);
        return buildErrorResponse(exception.getBindingResult(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<ErrorResponse> handleConstraintValidationException(
        ConstraintViolationException exception,
        HttpServletRequest request
    ) {
        List<ErrorDetailResponse> errorDetailResponseList = exception
            .getConstraintViolations()
            .stream()
            .map(constraintViolation -> {
                String fieldName = Strings.EMPTY;
                Path.Node lastNode = StreamSupport
                    .stream(constraintViolation.getPropertyPath().spliterator(), false)
                    .reduce((prev, next) -> next)
                    .orElse(null);
                if (Objects.nonNull(lastNode)) {
                    fieldName = lastNode.getName();
                }

                return new ErrorDetailResponse(fieldName, constraintViolation.getMessage());
            })
            .toList();

        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            errorDetailResponseList,
            errorDetailResponseList.stream().findFirst().orElse(new ErrorDetailResponse()).getCode(),
            new Meta(request.getRequestURI(), request.getMethod(), LocalDateTime.now().toString(), "")
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(EnumExceptionHandler.BaseSystemException.class)
    public ResponseEntity<ErrorResponse> handleSystemException(
        EnumExceptionHandler.BaseSystemException exception,
        HttpServletRequest request
    ) {
        LOGGER.error("A system error has occurred, error: {} for request {}", exception, request);
        return buildErrorResponse(exception, request);
    }

    @ExceptionHandler(EnumExceptionHandler.NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSystemNotFoundException(
        EnumExceptionHandler.BaseSystemException exception,
        HttpServletRequest request
    ) {
        LOGGER.warn("A system not found error has occurred, error: {} for request {}", exception, request);
        return buildErrorResponse(exception, request);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ErrorResponse> handleUnexpectedExceptions(Exception exception, HttpServletRequest request) {
        LOGGER.warn("Unexpected error has occurred, error: {} for request {}", exception, request);
        return buildErrorResponse(exception, request);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(
        EnumExceptionHandler.BaseSystemException exception,
        HttpServletRequest request
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
            exception.getStatus().value(),
            Arrays
                .stream(exception.getArgs())
                .map(arg -> new ErrorDetailResponse(arg, exception.getMessage()))
                .toList(),
            exception.getMessage(),
            new Meta(request.getRequestURI(), request.getMethod(), LocalDateTime.now().toString(), "")
        );

        return ResponseEntity.status(exception.getStatus()).body(errorResponse);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(
        BaseDomainException exception,
        HttpStatus status,
        HttpServletRequest request
    ) {
        String flattedArgs = String.join(",", exception.getArgs());

        ErrorResponse errorResponse = new ErrorResponse(
            status.value(),
            List.of(new ErrorDetailResponse(flattedArgs, exception.getKey())),
            exception.getMessage(),
            new Meta(request.getRequestURI(), request.getMethod(), LocalDateTime.now().toString(), "")
        );

        return ResponseEntity.status(status).body(errorResponse);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(
        BindingResult bindingResult,
        HttpStatus httpStatus,
        HttpServletRequest request
    ) {
        List<ErrorDetailResponse> errorDetailResponses = bindingResult
            .getFieldErrors()
            .stream()
            .map(fieldError -> new ErrorDetailResponse(fieldError.getField(), fieldError.getDefaultMessage()))
            .toList();

        String err = "";
        Optional<ErrorDetailResponse> firstError = errorDetailResponses.stream().findFirst();
        if (firstError.isPresent()) {
            err = firstError.get().getCode();
        }

        ErrorResponse ErrorResponse = new ErrorResponse(
            httpStatus.value(),
            errorDetailResponses,
            err,
            new Meta(request.getRequestURI(), request.getMethod(), LocalDateTime.now().toString(), "")
        );

        return ResponseEntity.status(httpStatus).body(ErrorResponse);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception ex, HttpServletRequest request) {
        ErrorDetailResponse errorDetailResponse = new ErrorDetailResponse();
        errorDetailResponse.setCode("system.unexpected-error-occurred.error");

        ErrorResponse ErrorResponse = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            List.of(errorDetailResponse),
            ex.getCause().toString(),
            new Meta(request.getRequestURI(), request.getMethod(), LocalDateTime.now().toString(), "")
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse);
    }
}
