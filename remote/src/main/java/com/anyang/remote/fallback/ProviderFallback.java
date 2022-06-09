package com.anyang.remote.fallback;

import com.anyang.common.base.Result;
import com.anyang.remote.provider.ProviderFeignClient;
import org.springframework.stereotype.Component;

@Component
public class ProviderFallback implements ProviderFeignClient {

    @Override
    public Result<?> getAll() {
        return Result.wrapError("-1", "fallback");
    }
}
