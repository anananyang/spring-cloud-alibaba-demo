package com.anyang.consumer.irule;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.ExtendBalancer;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class MyVersionRule extends AbstractLoadBalancerRule {

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
        try {
            String clusterName = nacosDiscoveryProperties.getClusterName();
            DynamicServerListLoadBalancer loadBalancer = (DynamicServerListLoadBalancer)this.getLoadBalancer();
            String name = loadBalancer.getName();
            NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
            // 获取到所有实例
            List<Instance> instances = namingService.selectInstances(name, true);
            if (CollectionUtils.isEmpty(instances)) {
                log.warn("no instance in service {}", name);
                return null;
            }
            // 找到目标版本实例
            String targetVersion = nacosDiscoveryProperties.getMetadata().get("version");
            if(targetVersion != null) {
                instances = instances.stream()
                                     .filter(instance -> targetVersion.equals(instance.getMetadata().get("version")))
                                     .collect(Collectors.toList());
            }

            if (CollectionUtils.isEmpty(instances)) {
                log.warn("no target version {} instance in service {}", targetVersion, name);
                return null;
            }
            List<Instance> instancesToChoose = instances;
            if (StringUtils.isNotBlank(clusterName)) {
                List<Instance> sameClusterInstances = (List)instances.stream().filter((instancex) -> {
                    return Objects.equals(clusterName, instancex.getClusterName());
                }).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(sameClusterInstances)) {
                    instancesToChoose = sameClusterInstances;
                } else {
                    log.warn("A cross-cluster call occurs，name = {}, clusterName = {}, instance = {}", new Object[]{name, clusterName, instances});
                }
            }

            Instance instance = ExtendBalancer.getHostByRandomWeight2(instancesToChoose);
            return new NacosServer(instance);

        } catch (Exception var9) {
            log.warn("NacosRule error", var9);
            return null;
        }


    }
}
