package com.wanted.babdoduk.sigungu.controller;

import com.wanted.babdoduk.common.response.ApiResponse;
import com.wanted.babdoduk.sigungu.dto.SiGunGuResponseDto;
import com.wanted.babdoduk.sigungu.service.SiGunGuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "시도, 시군구 목록")
public class SiGunGuController {

    private final SiGunGuService siGunGuService;
    @Operation(summary = "시도, 시군구 목록 조회", description = "시도, 시군구의 전체 목록과 상세 내용을 보여줍니다.")
    @GetMapping("/api/v1/sigungu/list")
    public ApiResponse<List<SiGunGuResponseDto>> siGunGuList() {
        return ApiResponse.ok(siGunGuService.getSiGunGuList());
    }
}
