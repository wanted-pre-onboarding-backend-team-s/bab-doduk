package com.wanted.babdoduk.batch.client;


import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.babdoduk.batch.client.response.success.ClientRestaurant;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Open API 동작 테스트")
class OpenRestaurantClientTest {

    @Autowired
    private OpenRestaurantClient openRestaurantClient;

    @Test
    @DisplayName("Open API 요청 성공")
    void executeOpenRestaurantApi() {
        // when
        List<ClientRestaurant> response = openRestaurantClient.execute(1, 10);

        // then
        assertThat(response).isNotNull();
        assertThat(response).hasSize(10);
    }
}