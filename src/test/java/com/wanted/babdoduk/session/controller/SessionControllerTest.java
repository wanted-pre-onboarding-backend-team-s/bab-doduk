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
import com.wanted.babdoduk.common.config.jwt.JwtConfig;
import com.wanted.babdoduk.common.config.mvc.WebMvcConfig;
import com.wanted.babdoduk.session.dto.LoginRequestDto;
import com.wanted.babdoduk.session.dto.LoginResultDto;
import com.wanted.babdoduk.session.service.LoginService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SessionController.class)
@Import({WebMvcConfig.class, JwtConfig.class})
@MockBean(JpaMetamodelMappingContext.class)
@MockBean(OpenRestaurantService.class)
@MockBean(RawRestaurantRepository.class)
class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @DisplayName("POST /api/v1/sessions")
    @Nested
    class postSessions {

        private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
        private static final String REFRESH_TOKEN = "REFRESH_TOKEN";

        @DisplayName("성공: 생성된 Access Token, Refresh Token을 반환")
        @Test
        void success() throws Exception {
            LoginResultDto loginResultDto = LoginResultDto.builder()
                    .accessToken(ACCESS_TOKEN)
                    .refreshToken(REFRESH_TOKEN)
                    .build();

            given(loginService.login(any(LoginRequestDto.class)))
                    .willReturn(loginResultDto);

            mockMvc.perform(post("/api/v1/sessions")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                        "username": "hsjkdss228",
                                        "password": "Password!1"
                                    }"""))
                    .andExpect(status().isCreated());

            verify(loginService).login(any(LoginRequestDto.class));
        }

        @DisplayName("실패: username 입력하지 않은 경우 예외 응답 반환")
        @Test
        void blankUsername() throws Exception {
            mockMvc.perform(post("/api/v1/sessions")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                        "password": "Password!1"
                                    }"""))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("username")));

            verify(loginService, never()).login(any(LoginRequestDto.class));
        }

        @DisplayName("실패: password 입력하지 않은 경우 예외 응답 반환")
        @Test
        void blankPassword() throws Exception {
            mockMvc.perform(post("/api/v1/sessions")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                        "username": "hsjkdss228",
                                        "password": null
                                    }"""))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("password")));

            verify(loginService, never()).login(any(LoginRequestDto.class));
        }
    }
}
