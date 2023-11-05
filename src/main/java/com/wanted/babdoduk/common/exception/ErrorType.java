package com.wanted.babdoduk.common.exception;

import com.wanted.babdoduk.restaurant.exception.ClosedRestaurantException;
import com.wanted.babdoduk.restaurant.exception.NotFoundRestaurantException;
import com.wanted.babdoduk.user.exception.DuplicatedUsernameException;
import com.wanted.babdoduk.user.exception.MismatchedReconfirmPasswordException;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    U001("U001", "이미 존재하는 계정명입니다.", DuplicatedUsernameException.class, HttpStatus.CONFLICT),
    U002("U002", "비밀번호 확인이 일치하지 않습니다.", MismatchedReconfirmPasswordException.class, HttpStatus.BAD_REQUEST),

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
