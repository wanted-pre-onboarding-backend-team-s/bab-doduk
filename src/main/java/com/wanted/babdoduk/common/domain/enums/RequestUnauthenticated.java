package com.wanted.babdoduk.common.domain.enums;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public enum RequestUnauthenticated {
    PostUsers(HttpMethod.POST.name(), "/api/v1/users"),
    PostSessions(HttpMethod.POST.name(), "/api/v1/sessions");

    private final String method;
    private final String path;

    private static final List<RequestUnauthenticated> REQUESTS_UNAUTHENTICATED
            = Arrays.stream(RequestUnauthenticated.values())
            .toList();

    public static boolean contains(HttpServletRequest request) {
        return REQUESTS_UNAUTHENTICATED
                .stream()
                .anyMatch(RequestUnauthenticated -> {
                    return RequestUnauthenticated.method.equals(request.getMethod())
                            && RequestUnauthenticated.path.equals(request.getRequestURI());
                });
    }
}
