package com.wanted.babdoduk.batch.client;

import com.wanted.babdoduk.batch.client.config.OpenRestaurantApiConfiguration;
import com.wanted.babdoduk.batch.client.response.success.ClientRestaurant;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "restaurantClient",
        url = "${feign.openapi.url}",
        configuration = OpenRestaurantApiConfiguration.class
)
public interface OpenRestaurantClient {

    @GetMapping("/GENRESTRT")
    List<ClientRestaurant> execute(
            @RequestParam(name = "pIndex", defaultValue = "1") int index,
            @RequestParam(name = "pSize", defaultValue = "1000") int size
    );

}
