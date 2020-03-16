package com.xiaqi.crawl.controller.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonResponse {

    private int code = 200;

    private String msg;

    private Object data;

    private static final CommonResponse DEFAULT_SUCCESS_RESPONSE = new CommonResponse(200,"success",null);

    private static final CommonResponse DEFAULT_FAIL_RESPONSE = new CommonResponse(500,"server error",null);

    public static CommonResponse getDefaultSuccessResponse() {
        return DEFAULT_SUCCESS_RESPONSE;
    }

    public static CommonResponse getDefaultFailResponse() {
        return DEFAULT_FAIL_RESPONSE;
    }
}
