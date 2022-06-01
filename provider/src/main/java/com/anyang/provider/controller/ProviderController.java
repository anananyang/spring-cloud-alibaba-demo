package com.anyang.provider.controller;

import com.anyang.common.base.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("resources")
public class ProviderController {

    @RequestMapping("getAll")
    @ResponseBody
    public Result<?> getAll() {
        Map<String, String> map = new HashMap<>();
        map.put("groupId", "com.anyang");
        map.put("artifactId", "common");
        map.put("version", "1.0-SNAPSHOT");
        return Result.wrapSuccess(map);
    }

}
