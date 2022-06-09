package com.anyang.consumer.config;

import com.alibaba.cloud.nacos.ribbon.NacosRule;
import com.anyang.consumer.irule.MyVersionRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

    @Configuration
    public class RibbonConfig {

        @Bean
        public IRule iRule() {
    //        return new NacosRule();
            return new MyVersionRule();
        }
    }
