package com.wanted.babdoduk.batch.client.response.success;

import com.wanted.babdoduk.batch.client.response.common.ClientResponseStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Head {
    private long listTotalCount;
    private ClientResponseStatus clientResponseStatus;
    private String apiVersion;

    @Builder
    public Head(long listTotalCount, ClientResponseStatus clientResponseStatus, String apiVersion) {
        this.listTotalCount = listTotalCount;
        this.clientResponseStatus = clientResponseStatus;
        this.apiVersion = apiVersion;
    }
}