package com.raii.synthetichumancorestarter;

import com.raii.synthetichumancorestarter.command.service.exceptions.ExecutionQueueIsFullException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class SyntheticHumanCoreStarterExceptionHandler {
    @ExceptionHandler(ExecutionQueueIsFullException.class)
    public ErrorResponse handleExecutionQueueIsFullException(ExecutionQueueIsFullException e) {
        return constructErrorResponse(e, HttpStatus.TOO_MANY_REQUESTS, "Android execution queue is exhausted");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException e) {
        return constructErrorResponse(e, HttpStatus.BAD_REQUEST, "Invalid input data format");
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleGenericException(Exception e) {
        return constructErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }

    public ErrorResponse constructErrorResponse(Exception e, HttpStatus statusCode, String title) {
        if (statusCode.is5xxServerError()) {
            log.error("Internal server error: `{}` due `{}`",
                    e.getMessage(),
                    Optional.ofNullable(e.getCause()).map(Throwable::getMessage).orElse("n/a"));
        }

        return ErrorResponse.builder(e, statusCode, title)
                .build();
    }
}
