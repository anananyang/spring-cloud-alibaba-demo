package com.anyang.consumer.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.anyang.common.base.Result;
import com.anyang.consumer.remote.ProviderFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RefreshScope
@Controller
@RequestMapping("resources")
@Slf4j
public class ConsumerController {

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private RestTemplate loadBalanceRestTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private ProviderFeignClient providerFeignClient;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Value("${my.user.name}")
    private String myName;
    @Value("${my.user.password}")
    private String myPassword;

    private static final String PROVIDRE_URL = "http://provider-service/provider/resources/getAll";

    @RequestMapping("getAll")
    @ResponseBody
    public Result<?> getAll() {
        // 手动的负载均衡，无需在 restTemplate 上添加负载均衡
        ServiceInstance serviceInstance = loadBalancerClient.choose("provider-service");
        String url = String.format("http://%s:%s/provider/resources/getAll",
                serviceInstance.getHost(),
                serviceInstance.getPort());

        return restTemplate.getForObject(url, Result.class);
    }


    @RequestMapping("getAllWithLoadBanace")
    @ResponseBody
    public Result<?> getAllWithLoadBanace() {
        // 自动负载均衡，无需在 restTemplate 上添加负载均
        return loadBalanceRestTemplate.getForObject(PROVIDRE_URL, Result.class);
    }


    @RequestMapping("getAllWithFeign")
    @ResponseBody
    public Result<?> getAllWithFeign() {
        // 自动负载均衡，无需在 restTemplate 上添加负载均
        return providerFeignClient.getAll();
    }

    @RequestMapping("services/{service}")
    @ResponseBody
    public Result<?> getServiceInfo(@PathVariable("service") String service) {
        Object object = discoveryClient.getInstances(service);
        return Result.wrapSuccess(object);
    }

    @RequestMapping("services")
    @ResponseBody
    public Result<?> getServices() {
        log.info(discoveryClient.description());
        Object object = discoveryClient.getServices();
        return Result.wrapSuccess(object);
    }

    @RequestMapping("getMyConfig")
    @ResponseBody
    public Result<?> getMyConfig() {
        Map<String, Object> map = new HashMap<>();
        map.put("myName", myName);
        map.put("myPassword", myPassword);
        return Result.wrapSuccess(map);
    }

}
