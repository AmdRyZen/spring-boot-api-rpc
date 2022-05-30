package com.mltt.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.mltt.biz.model.FUser;
import com.mltt.service.ApiService;
import com.mltt.service.DubboApiService;
import org.apache.dubbo.config.annotation.DubboReference;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Resource
    ApiService apiService;
    @Resource
    JdbcTemplate jdbcTemplate;
    @NacosValue(value = "${username.aa:none}", autoRefreshed = true)
    private String username;

    @RequestMapping("/config")
    public String config() {
        return this.username;
    }

    @DubboReference(version = "1.0", group = "dubboApi", interfaceClass = DubboApiService.class)
    public DubboApiService dubboRpcService;

    @RequestMapping("/dubbo")
    public FUser dubbo() {
        return dubboRpcService.getFuserList();
    }


}
