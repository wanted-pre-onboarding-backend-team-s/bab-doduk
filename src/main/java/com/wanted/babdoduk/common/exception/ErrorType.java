package com.wanted.babdoduk.common.exception;

import com.wanted.babdoduk.restaurant.exception.NotFoundRestaurantException;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    U001("U001", "에러 메시지를 담습니다.", WantedException.class, HttpStatus.NOT_FOUND),

    R001("R001", "식당이 존재하지 않습니다.", NotFoundRestaurantException.class, HttpStatus.NOT_FOUND);

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
