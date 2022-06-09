package com.anyang.consumer.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class MyRequestOriginParser implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest request) {
        String origin = request.getParameter("origin");
        if(origin == null) {
            origin = request.getHeader("origin");
        }
//        if(origin == null) {
//            throw new IllegalArgumentException("server origin 未指定");
//        }
        return origin;
    }
}
