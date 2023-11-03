package com.wanted.babdoduk.common.exception;

import com.wanted.babdoduk.sigungu.exception.FailedGetSiGunGuException;
import com.wanted.babdoduk.user.domain.exception.NotFoundUserException;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    U001("U001", "사용자를 찾을 수 없습니다.", NotFoundUserException.class, HttpStatus.NOT_FOUND),
    S002("S002", "시도, 시군구 목록을 가져오지 못했습니다.", FailedGetSiGunGuException.class, HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final Class<? extends WantedException> classType;
    private final HttpStatus httpStatus;
    private static final List<ErrorType> errorTypes = Arrays.stream(ErrorType.values()).toList();

    public static ErrorType of(Class<? extends WantedException> classType) {
        return errorTypes.stream()
                .filter(it -> it.classType.equals(classType))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
