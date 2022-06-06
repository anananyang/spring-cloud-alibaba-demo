package com.anyang.provider.controller;

import com.anyang.common.base.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("resources")
@Slf4j
public class ProviderController {

    @Value("${server.port}")
    private String port;

    @RequestMapping("getAll")
    @ResponseBody
    public Result<?> getAll() {
        log.info("当前 provider 的端口：{}", port);
        Map<String, String> map = new HashMap<>();
        map.put("groupId", "com.anyang");
        map.put("artifactId", "common");
        map.put("version", "1.0-SNAPSHOT");
        return Result.wrapSuccess(map);
    }

}
