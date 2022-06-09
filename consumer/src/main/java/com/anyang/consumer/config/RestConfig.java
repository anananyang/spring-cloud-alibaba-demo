package com.anyang.consumer.config;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.anyang.consumer.sentinel.MyFallBack;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @SentinelRestTemplate(
            blockHandlerClass = MyFallBack.class,
            blockHandler = "blockForRestTemplate",
            fallbackClass = MyFallBack.class,
            fallback = "fallbackForRestTemplate"
    )
    @LoadBalanced
    @Bean
    RestTemplate loadBalanceRestTemplate() {
        return new RestTemplate();
    }

}
