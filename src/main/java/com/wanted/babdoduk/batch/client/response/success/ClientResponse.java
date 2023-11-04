package com.wanted.babdoduk.batch.client.response.success;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanted.babdoduk.batch.client.serializer.CustomClientResponseDeserializer;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@JsonDeserialize(using = CustomClientResponseDeserializer.class)
@NoArgsConstructor
public class ClientResponse {

    private Head head;
    private List<RawRestaurant> rawRestaurants;


    @Builder
    public ClientResponse(Head head, List<RawRestaurant> rawRestaurants) {
        this.head = head;
        this.rawRestaurants = rawRestaurants;
    }
}
