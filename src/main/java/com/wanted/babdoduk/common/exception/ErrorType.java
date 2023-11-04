package com.wanted.babdoduk.common.exception;

import com.wanted.babdoduk.sigungu.exception.FailedGetSiGunGuException;
import com.wanted.babdoduk.user.domain.exception.NotFoundUserException;
import com.wanted.babdoduk.restaurant.exception.ClosedRestaurantException;
import com.wanted.babdoduk.restaurant.exception.NotFoundRestaurantException;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    U001("U001", "사용자를 찾을 수 없습니다.", NotFoundUserException.class, HttpStatus.NOT_FOUND),
    S001("S001", "시도, 시군구 목록을 불러올 수 없습니다.", FailedGetSiGunGuException.class, HttpStatus.INTERNAL_SERVER_ERROR),
    R001("R001", "식당이 존재하지 않습니다.", NotFoundRestaurantException.class, HttpStatus.NOT_FOUND),
    R002("R002", "영업하지 않는 식당입니다.", ClosedRestaurantException.class, HttpStatus.NOT_FOUND);

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
