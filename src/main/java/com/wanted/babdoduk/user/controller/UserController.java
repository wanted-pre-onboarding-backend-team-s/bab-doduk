package com.wanted.babdoduk.user.controller;

import com.wanted.babdoduk.common.response.ApiResponse;
import com.wanted.babdoduk.user.dto.CreateUserRequestDto;
import com.wanted.babdoduk.user.dto.CreateUserResponseDto;
import com.wanted.babdoduk.user.service.CreateUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final CreateUserService createUserService;

    @Operation(summary = "회원가입")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateUserResponseDto> createUser(
            @Validated @RequestBody CreateUserRequestDto createUserRequestDto
    ) {
        return ApiResponse.created(createUserService.createUser(createUserRequestDto));
    }
}
