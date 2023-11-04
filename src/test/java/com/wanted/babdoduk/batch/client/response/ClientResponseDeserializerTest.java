package com.wanted.babdoduk.batch.client.response;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.babdoduk.batch.MockResponseTest;
import com.wanted.babdoduk.batch.client.response.common.ClientResponseStatus;
import com.wanted.babdoduk.batch.client.response.success.ClientResponse;
import com.wanted.babdoduk.batch.client.response.success.Head;
import com.wanted.babdoduk.batch.client.response.success.ClientRestaurant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("OpenAPI 응답값 역직렬화 테스트")
class ClientResponseDeserializerTest extends MockResponseTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Open API 응답 값을 ClientResponse 로 역직렬화 - 성공")
    void deserializeClientResponse() throws JsonProcessingException{
        // when
        ClientResponse clientResponse = objectMapper.readValue(SUCCESS_RESPONSE_JSON, ClientResponse.class);

        // then
        // 1. clientResponse -> head
        Head head = clientResponse.getHead();
        assertThat(head).isNotNull();
        assertThat(clientResponse.getHead())
                .returns("1.0", from(Head::getApiVersion))
                .returns(442768L, from(Head::getListTotalCount));

        // 2. clientResponse -> head -> status
        ClientResponseStatus responseStatus = clientResponse.getHead().getClientResponseStatus();
        assertThat(responseStatus).isNotNull();
        assertThat(responseStatus)
                .returns("INFO-000", from(ClientResponseStatus::getCode))
                .returns("정상 처리되었습니다.", from(ClientResponseStatus::getMessage));

        // 3. clientResponse -> restaurants
        ClientRestaurant clientRestaurant = clientResponse.getClientRestaurants().get(0);
        assertThat(clientResponse.getClientRestaurants()).hasSize(1);
        assertThat(clientRestaurant).isNotNull();
        assertThat(clientRestaurant)
                .extracting(
                        ClientRestaurant::getSigunNm,
                        ClientRestaurant::getLat,
                        ClientRestaurant::getLon,
                        ClientRestaurant::getManageNo,
                        ClientRestaurant::getBsnStateName,
                        ClientRestaurant::getCuisineType,
                        ClientRestaurant::getRoadAddr,
                        ClientRestaurant::getJibunAddr
                )
                .containsExactly(
                        "파주시",
                        "37.7319285386",
                        "126.7518549307",
                        "4060000-101-2014-00404",
                        "폐업",
                        "김밥(도시락)",
                        "경기도 파주시 미래로602번길 31, 1층 104호 (와동동, 아이프라자)",
                        "경기도 파주시 와동동 1302-4 아이프라자 104호"
                );
    }

    @Test
    @DisplayName("Open API 응답 상태 값 ClientResponseStatus 로 역직렬화 - 성공")
    void deserializeClientResponseStatus() throws JsonProcessingException {
        // when
        ClientResponseStatus status = objectMapper.readValue(HEAD_RESULT_JSON, ClientResponseStatus.class);

        // then
        assertThat(status.getCode()).isEqualTo("ERROR-333");
        assertThat(status.getMessage()).isEqualTo("요청위치 값의 타입이 유효하지 않습니다.요청위치 값은 정수를 입력하세요.");
    }
}
