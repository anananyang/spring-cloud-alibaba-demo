package com.anyang.provider.controller;

import com.anyang.common.base.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("restTemplate")
public class RestTemplateController {

    /**
     * GET 请求，无参数
     *
     * @return
     */
    @RequestMapping("getRequst")
    @ResponseBody
    public Result<?> getRequst() {
        return Result.wrapSuccess("GET 请求，无参数");
    }

    /**
     * GET 请求，有参
     *
     * @return
     */
    @RequestMapping("getRequestWithParam")
    @ResponseBody
    public Result<?> getRequestWithParam(@RequestParam("token") String token) {
        return Result.wrapSuccess("GET 请求，参数：token = " + token);
    }


    /**
     * POST 请求，JSON
     *
     * @return
     */
    @RequestMapping("postRequestWithForm")
    @ResponseBody
    public Result<?> postRequestWithForm(@RequestParam("name") String name,
                                         @RequestParam("age") Integer age,
                                         @RequestParam("sex") String sex,
                                         @RequestParam("mobile") String mobile) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("age", age);
        map.put("sex", sex);
        map.put("mobile", mobile);

        return Result.wrapSuccess(map);
    }

    /**
     * POST 请求，JSON
     *
     * @return
     */
    @RequestMapping("postRequestWithJson")
    @ResponseBody
    public Result<?> postRequestWithJson(@RequestBody Map<String, Object> map) {
        return Result.wrapSuccess(map);
    }

}
