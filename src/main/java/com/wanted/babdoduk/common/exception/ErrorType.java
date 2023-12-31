package com.wanted.babdoduk.common.exception;

import com.wanted.babdoduk.common.interceptor.exception.InvalidRequestHeaderAuthorizationException;
import com.wanted.babdoduk.common.interceptor.exception.MissingRequestHeaderAuthorizationException;
import com.wanted.babdoduk.session.exception.AccessTokenExpiredException;
import com.wanted.babdoduk.session.exception.PasswordMismatchedException;
import com.wanted.babdoduk.session.exception.RefreshTokenDifferentException;
import com.wanted.babdoduk.session.exception.RefreshTokenExpiredException;
import com.wanted.babdoduk.session.exception.RefreshTokenNotIssuedException;
import com.wanted.babdoduk.session.exception.TokenDecodingFailedException;
import com.wanted.babdoduk.session.exception.TokenIssuanceFailedException;
import com.wanted.babdoduk.restaurant.domain.review.exception.ReviewNotFoundException;
import com.wanted.babdoduk.sigungu.exception.FailedGetSiGunGuException;
import com.wanted.babdoduk.user.exception.UserNotFoundException;
import com.wanted.babdoduk.restaurant.exception.ClosedRestaurantException;
import com.wanted.babdoduk.restaurant.exception.NotFoundRestaurantException;
import com.wanted.babdoduk.user.exception.UsernameDuplicatedException;
import com.wanted.babdoduk.user.exception.ReconfirmPasswordMismatchedException;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    U001("U001", "이미 존재하는 계정명입니다.", UsernameDuplicatedException.class, HttpStatus.CONFLICT),
    U002("U002", "비밀번호 확인이 일치하지 않습니다.", ReconfirmPasswordMismatchedException.class, HttpStatus.BAD_REQUEST),
    U003("U003", "존재하지 않는 사용자입니다.", UserNotFoundException.class, HttpStatus.NOT_FOUND),

    SE001("SE001", "비밀번호가 일치하지 않습니다.", PasswordMismatchedException.class, HttpStatus.BAD_REQUEST),

    AU001("AU001", "액세스 토큰이 없습니다.", MissingRequestHeaderAuthorizationException.class, HttpStatus.UNAUTHORIZED),
    AU002("AU002", "잘못된 액세스 토큰 전달자 형식입니다.", InvalidRequestHeaderAuthorizationException.class, HttpStatus.UNAUTHORIZED),
    AU003("AU003", "액세스 토큰이 만료되었습니다.", AccessTokenExpiredException.class, HttpStatus.UNAUTHORIZED),

    T001("T001", "토큰 생성에 실패했습니다.", TokenIssuanceFailedException.class, HttpStatus.INTERNAL_SERVER_ERROR),
    T002("T002", "토큰 디코딩에 실패했습니다.", TokenDecodingFailedException.class, HttpStatus.INTERNAL_SERVER_ERROR),
    T003("T003", "발행된 리프레시 토큰이 없습니다.", RefreshTokenNotIssuedException.class, HttpStatus.UNAUTHORIZED),
    T004("T004", "발행된 리프레시 토큰과 다릅니다.", RefreshTokenDifferentException.class, HttpStatus.UNAUTHORIZED),
    T005("T005", "리프레시 토큰이 만료되었습니다.", RefreshTokenExpiredException.class, HttpStatus.UNAUTHORIZED),

    S001("S001", "시도, 시군구 목록을 불러올 수 없습니다.", FailedGetSiGunGuException.class, HttpStatus.INTERNAL_SERVER_ERROR),
    R001("R001", "식당이 존재하지 않습니다.", NotFoundRestaurantException.class, HttpStatus.NOT_FOUND),
    R002("R002", "영업하지 않는 식당입니다.", ClosedRestaurantException.class, HttpStatus.NOT_FOUND),
    V001("V001", "존재하지 않는 리뷰입니다.", ReviewNotFoundException.class, HttpStatus.NOT_FOUND);

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
