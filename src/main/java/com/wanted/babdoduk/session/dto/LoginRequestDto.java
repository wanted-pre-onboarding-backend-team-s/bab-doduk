package com.wanted.babdoduk.session.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class LoginRequestDto {
    @Schema(title = "Username", description = "계정명")
    @NotBlank
    private String username;

    @Schema(title = "Password", description = "비밀번호")
    @NotBlank
    private String password;
}
