package com.wanted.babdoduk.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WantedException extends RuntimeException {

    private final ErrorType errorType = ErrorType.of(this.getClass());
}
