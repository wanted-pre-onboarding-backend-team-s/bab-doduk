package com.wanted.babdoduk.sigungu.controller;

import com.wanted.babdoduk.common.response.ApiResponse;
import com.wanted.babdoduk.sigungu.dto.SiGunGuResponseDto;
import com.wanted.babdoduk.sigungu.service.SiGunGuService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class SiGunGuController {

    private final SiGunGuService siGunGuService;

    @GetMapping("/api/v1/sigungu/list")
    public ResponseEntity<ApiResponse<List<SiGunGuResponseDto>>> siGunGuList() {
        return ResponseEntity.ok(ApiResponse.toResponse(siGunGuService.getSiGunGuList()));
    }
}
