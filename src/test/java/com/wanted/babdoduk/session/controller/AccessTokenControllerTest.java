package com.wanted.babdoduk.session.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.wanted.babdoduk.batch.client.RawRestaurantRepository;
import com.wanted.babdoduk.batch.service.OpenRestaurantService;
import com.wanted.babdoduk.session.dto.AccessTokenReissuanceRequestDto;
import com.wanted.babdoduk.session.dto.AccessTokenReissuanceResponseDto;
import com.wanted.babdoduk.session.service.ReissueAccessTokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AccessTokenController.class)
@MockBean(JpaMetamodelMappingContext.class)
@MockBean(OpenRestaurantService.class)
@MockBean(RawRestaurantRepository.class)
class AccessTokenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReissueAccessTokenService reissueAccessTokenService;

    @DisplayName("POST /api/v1/access-tokens")
    @Nested
    class PostAccessTokens {

        private static final String ACCESS_TOKEN = "ACCESS_TOKEN";

        @DisplayName("성공: 재발행된 Access Token 반환")
        @Test
        void success() throws Exception {
            AccessTokenReissuanceResponseDto accessTokenReissuanceResponseDto
                    = AccessTokenReissuanceResponseDto.builder()
                    .accessToken(ACCESS_TOKEN)
                    .build();

            given(reissueAccessTokenService
                    .reissueAccessToken(any(AccessTokenReissuanceRequestDto.class)))
                    .willReturn(accessTokenReissuanceResponseDto);

            mockMvc.perform(post("/api/v1/access-tokens")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                        "refreshToken": "REFRESH_TOKEN"
                                    }"""))
                    .andExpect(status().isCreated());

            verify(reissueAccessTokenService)
                    .reissueAccessToken(any(AccessTokenReissuanceRequestDto.class));
        }

        @DisplayName("실패: refreshToken 입력하지 않은 경우 예외 응답 반환")
        @Test
        void blankRefreshToken() throws Exception {
            mockMvc.perform(post("/api/v1/access-tokens")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content("{}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("refreshToken")));

            verify(reissueAccessTokenService, never())
                    .reissueAccessToken(any(AccessTokenReissuanceRequestDto.class));
        }
    }
}
