package com.anyang.consumer.sentinel;

import com.alibaba.cloud.sentinel.rest.SentinelClientHttpResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.anyang.common.base.Result;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;


import java.util.HashMap;
import java.util.Map;

public class MyFallBack {

    private final static String fallback_for_restTemplate = JSON.toJSONString(Result.wrapError("", "fallback For RestTemplate"));
    private final static String block_for_restTemplate = JSON.toJSONString(Result.wrapError("", "block For RestTemplate"));


    public static Result<?> fallback(String a, String b) {
        Map<String, Object> map = new HashMap<>();
        map.put("a", "MyFallBack.fallback");
        map.put("b", "MyFallBack.fallback");
        return Result.wrapSuccess(map);
    }


    /**
     * restTemplate 整合 sentinel，如果 restTemplate 的拦截器抛出异常，则会执行
     * @param request
     * @param body
     * @param execution
     * @param blockException
     * @return
     */
    public static SentinelClientHttpResponse  fallbackForRestTemplate(HttpRequest request,
                                                                     byte[] body,
                                                                     ClientHttpRequestExecution execution,
                                                                     BlockException blockException) {

        return new SentinelClientHttpResponse(fallback_for_restTemplate);
    }


    /**
     * restTemplate 整合 sentinel，如果 restTemplate 的拦截器抛出异常，则会执行
     * @param request
     * @param body
     * @param execution
     * @param blockException
     * @return
     */
    public static SentinelClientHttpResponse  blockForRestTemplate(HttpRequest request,
                                                                      byte[] body,
                                                                      ClientHttpRequestExecution execution,
                                                                      BlockException blockException) {

        return new SentinelClientHttpResponse(block_for_restTemplate);
    }

}
