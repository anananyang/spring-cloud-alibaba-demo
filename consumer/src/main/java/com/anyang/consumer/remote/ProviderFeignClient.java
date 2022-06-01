package com.anyang.consumer.remote;

import com.anyang.common.base.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "provider-service")
public interface ProviderFeignClient {

    @RequestMapping("/provider/resources/getAll")
    Result<?> getAll();

}
