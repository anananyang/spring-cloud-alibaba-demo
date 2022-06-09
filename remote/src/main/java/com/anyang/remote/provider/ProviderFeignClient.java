package com.anyang.remote.provider;

import com.anyang.common.base.Result;
import com.anyang.remote.fallback.ProviderFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "provider-service", fallback = ProviderFallback.class)
public interface ProviderFeignClient {

    @RequestMapping("/provider/resources/getAll")
    Result<?> getAll();

}
