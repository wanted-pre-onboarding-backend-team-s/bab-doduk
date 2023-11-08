package com.wanted.babdoduk.common.interceptor;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.wanted.babdoduk.common.domain.enums.RequestUnauthenticated;
import com.wanted.babdoduk.common.interceptor.exception.InvalidRequestHeaderAuthorizationException;
import com.wanted.babdoduk.common.interceptor.exception.MissingRequestHeaderAuthorizationException;
import com.wanted.babdoduk.common.util.JwtUtil;
import com.wanted.babdoduk.session.exception.AccessTokenExpiredException;
import com.wanted.babdoduk.session.exception.TokenDecodingFailedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        if (RequestUnauthenticated.contains(request)) {
            return true;
        }

        String authorization = request.getHeader(AUTHORIZATION);

        if (authorization == null) {
            throw new MissingRequestHeaderAuthorizationException();
        }

        if (!authorization.startsWith(BEARER)) {
            throw new InvalidRequestHeaderAuthorizationException();
        }

        String accessToken = authorization.substring(BEARER.length());

        try {
            Long userId = jwtUtil.decodeAccessToken(accessToken);

            request.setAttribute("userId", userId);

            return true;
        } catch (TokenExpiredException exception) {
            throw new AccessTokenExpiredException();

        } catch (JWTDecodeException exception) {
            throw new TokenDecodingFailedException();
        }
    }
}
