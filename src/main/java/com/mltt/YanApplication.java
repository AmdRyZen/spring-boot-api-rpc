package com.mltt;

import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan("com.mltt.mapper")
@EnableDubbo
@NacosConfigurationProperties(dataId = "spring-boot-api-native.yml", autoRefreshed = true)
@EnableDiscoveryClient
@EnableFeignClients
@EnableCaching
@SpringBootApplication
public class YanApplication {
	public static void main(String[] args) {
		SpringApplication.run(YanApplication.class, args);
	}
}
