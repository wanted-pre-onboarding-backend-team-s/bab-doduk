package com.wanted.babdoduk.common.exception;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    private static final String NOT_BLANK_MESSAGE = "미입력 데이터가 존재합니다: ";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ) {
        log.error("[error] type: {}, status: {}, message: {}",
                exception.getClass().getSimpleName(),
                exception.getStatusCode(),
                exception.getMessage());

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        String message = mergeFieldsIntoMessage(fieldErrors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(String.valueOf(HttpStatus.BAD_REQUEST.value()), message));
    }

    private String mergeFieldsIntoMessage(List<FieldError> fieldErrors) {
        StringBuilder stringBuilder = new StringBuilder(NOT_BLANK_MESSAGE);

        fieldErrors.forEach(fieldError -> stringBuilder
                .append(fieldError.getField())
                .append(", "));

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        return stringBuilder.toString();
    }

    @ExceptionHandler(WantedException.class)
    protected ResponseEntity<ErrorResponse> handleWantedException(WantedException exception) {
        ErrorType errorType = exception.getErrorType();

        log.error("[error] type: {}, status: {}, message: {}",
                errorType.getClassType().getSimpleName(),
                errorType.getHttpStatus(),
                errorType.getMessage());

        return ResponseEntity
                .status(errorType.getHttpStatus())
                .body(ErrorResponse.of(errorType));
    }
}
