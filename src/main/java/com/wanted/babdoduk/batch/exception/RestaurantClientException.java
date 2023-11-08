package com.wanted.babdoduk.batch.exception;

import com.wanted.babdoduk.batch.client.response.common.ClientResultCode;
import feign.FeignException.FeignClientException;
import feign.Request;

public class RestaurantClientException extends FeignClientException {

    public RestaurantClientException(String code, Request request) {
        super(500, ClientResultCode.findCode(code).getMessage(), request, null, null);
    }
}
