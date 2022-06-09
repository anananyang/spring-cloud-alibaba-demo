package com.anyang.consumer.controller;

import com.anyang.common.base.Result;
import com.anyang.consumer.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("restTemplate")
@Slf4j
public class RestTemplateController {

    @Resource
    private RestTemplate loadBalanceRestTemplate;

    private final static String GET_URL = "http://provider-service/provider/restTemplate/getRequst";
    private final static String GET_WITH_PARAM_URL_1 = "http://provider-service/provider/restTemplate/getRequestWithParam?token={0}";
    private final static String GET_WITH_PARAM_URL_2 = "http://provider-service/provider/restTemplate/getRequestWithParam?token={token}";
    private final static String POST_URL_1 = "http://provider-service/provider/restTemplate/postRequestWithForm";
    private final static String POST_URL_2 = "http://provider-service/provider/restTemplate/postRequestWithJson";

    @RequestMapping("getForObject")
    @ResponseBody
    public Result<?> getForObject() {
        return loadBalanceRestTemplate.getForObject(GET_URL, Result.class);
    }

    @RequestMapping("getForEntity")
    @ResponseBody
    public Result<?> getForEntity() {
        ResponseEntity<Result> responseEntity = loadBalanceRestTemplate.getForEntity(GET_URL, Result.class);
        log.info("status code: {}", responseEntity.getStatusCode());
        log.info("http headers: {}", responseEntity.getHeaders());
        return (Result<?>) responseEntity.getBody();
    }


    @RequestMapping("getForObjectWithParam")
    @ResponseBody
    public Result<?> getForObjectWithParam() {
        // 第一种方式
        Object[] params = {"123456"};
        return loadBalanceRestTemplate.getForObject(GET_WITH_PARAM_URL_1, Result.class, params);

        // 第二种方式
//        Map<String, String> map = new HashMap<>();
//        map.put("token", "123456");
//        return loadBalanceRestTemplate.getForObject(GET_WITH_PARAM_URL_2, Result.class, map);
    }


    /**
     * POST 请求，表单
     *
     * @return
     */
    @RequestMapping("postRequestWithForm")
    @ResponseBody
    public Result<?> postRequestWithParam() {
        // 表单，第一种方式，必须使用 MultiValueMap。
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("name", "anyang");
        map.add("age", "28");
        map.add("sex", "male");
        map.add("mobile", "13611111111");
        return loadBalanceRestTemplate.postForObject(POST_URL_1, map, Result.class);

        // 表单，第一种方式，必须使用 user。 测试无效
//        User user = new User();
//        user.setName("anyang");
//        user.setAge(28);
//        user.setSex("male");
//        user.setMobile("13611111111");
//        return loadBalanceRestTemplate.postForObject(POST_URL_1, user, Result.class);
    }


    /**
     * POST 请求，表单
     *
     * @return
     */
    @RequestMapping("postRequestWithJson")
    @ResponseBody
    public Result<?> postRequestWithJson() {
        // 表单，第一种方式，必须使用 MultiValueMap。
        User user = new User();
        user.setName("anyang");
        user.setAge(28);
        user.setSex("male");
        user.setMobile("13611111111");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);

        return loadBalanceRestTemplate.postForObject(POST_URL_2, httpEntity, Result.class);
    }

}
