package com.wanted.babdoduk.batch.client.response.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.wanted.babdoduk.batch.exception.spec.ClientResultCodeNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Open API 결과 코드 테스트")
class ClientResultCodeTest {

    @DisplayName("코드 이름으로 실제 Enum 코드 찾기 - 성공")
    @ParameterizedTest(name = "코드 이름: {0}")
    @ValueSource(strings = {
            "ERROR-310", "ERROR-290", "ERROR-333", "INFO-200",
            "ERROR-300", "ERROR-336", "ERROR-337", "ERROR-500",
            "ERROR-600", "ERROR-601", "INFO-000", "INFO-300"
    })
    void testFindCode(String code) {
        // when
        ClientResultCode resultCode = ClientResultCode.findCode(code);

        // then
        assertThat(resultCode.getCode()).isEqualTo(code);
    }

    @Test
    @DisplayName("정의되지 않는 API 코드의 경우 예외 던진다. - 실패")
    void throwExceptionNotDefinedCode() {
        // when
        String code = "ERROR-876";

        // then
        assertThatThrownBy(() -> ClientResultCode.findCode(code))
                .isInstanceOf(ClientResultCodeNotFoundException.class);
    }

}