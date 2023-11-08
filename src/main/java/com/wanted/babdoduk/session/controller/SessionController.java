package com.wanted.babdoduk.session.controller;

import com.wanted.babdoduk.common.response.ApiResponse;
import com.wanted.babdoduk.session.dto.LoginRequestDto;
import com.wanted.babdoduk.session.dto.LoginResultDto;
import com.wanted.babdoduk.session.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "세션")
@RestController
@RequestMapping("/api/v1/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final LoginService loginService;

    @Operation(summary = "로그인")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<LoginResultDto> sessions(
            @Valid @RequestBody LoginRequestDto loginRequestDto
    ) {
        return ApiResponse.created(loginService.login(loginRequestDto));
    }
}
