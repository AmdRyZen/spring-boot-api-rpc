package com.mltt.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "spring-boot-api")
public interface FeignApiService {
    @RequestMapping(value = "api/feign", method = RequestMethod.GET)
    String getFuserList();
}
