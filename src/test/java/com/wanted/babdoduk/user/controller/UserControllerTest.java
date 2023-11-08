package com.wanted.babdoduk.user.controller;

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
import com.wanted.babdoduk.user.dto.CreateUserRequestDto;
import com.wanted.babdoduk.user.dto.CreateUserResponseDto;
import com.wanted.babdoduk.user.service.CreateUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
@MockBean(OpenRestaurantService.class)
@MockBean(RawRestaurantRepository.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateUserService createUserService;

    @DisplayName("POST /api/v1/users")
    @Nested
    class postUsers {

        private static final Long USER_ID = 11122L;

        @DisplayName("성공: 생성된 User Entity 식별자를 반환")
        @Test
        void success() throws Exception {
            CreateUserResponseDto createUserResponseDto = CreateUserResponseDto.builder()
                    .userId(USER_ID)
                    .build();

            given(createUserService.createUser(any(CreateUserRequestDto.class)))
                    .willReturn(createUserResponseDto);

            mockMvc.perform(post("/api/v1/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                        "username": "hsjkdss228",
                                        "password": "Password!1",
                                        "reconfirmPassword": "Password!1"
                                    }
                                    """))
                    .andExpect(status().isCreated());

            verify(createUserService).createUser(any(CreateUserRequestDto.class));
        }

        @DisplayName("실패: username 입력하지 않은 경우 예외 응답 반환")
        @Test
        void blankUsername() throws Exception {
            mockMvc.perform(post("/api/v1/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                        "username": "          ",
                                        "password": "Password!1",
                                        "reconfirmPassword": "Password!1"
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("username")));

            verify(createUserService, never()).createUser(any(CreateUserRequestDto.class));
        }

        @DisplayName("실패: password 입력하지 않은 경우 예외 응답 반환")
        @Test
        void blankPassword() throws Exception {
            mockMvc.perform(post("/api/v1/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                        "username": "hsjkdss228",
                                        "reconfirmPassword": "Password!1"
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("password")));

            verify(createUserService, never()).createUser(any(CreateUserRequestDto.class));
        }

        @DisplayName("실패: reconfirmPassword 입력하지 않은 경우 예외 응답 반환")
        @Test
        void blankReconfirmPassword() throws Exception {
            mockMvc.perform(post("/api/v1/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                        "username": "hsjkdss228",
                                        "password": "Password@2",
                                        "reconfirmPassword": null
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("reconfirmPassword")));

            verify(createUserService, never()).createUser(any(CreateUserRequestDto.class));
        }

        @DisplayName("실패: 예외 응답에는 올바르지 않은 입력이 주어진 모든 필드 명칭을 포함")
        @Test
        void errorMessageContainsAll() throws Exception {
            mockMvc.perform(post("/api/v1/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content("{}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("username")))
                    .andExpect(content().string(containsString("password")))
                    .andExpect(content().string(containsString("reconfirmPassword")));

            verify(createUserService, never()).createUser(any(CreateUserRequestDto.class));
        }
    }
}
