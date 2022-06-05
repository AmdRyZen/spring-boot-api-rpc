package com.mltt.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.mltt.biz.model.FUser;
import com.mltt.exception.ServiceException;
import com.mltt.service.ApiService;
import com.mltt.service.DubboApiService;
import com.mltt.utils.ApiResultUtils;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.annotation.DubboReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
public class ApiController {
    private static final Logger log = LoggerFactory.getLogger(ApiController.class);
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
    public ApiResultUtils<FUser> dubbo() throws ServiceException {
        log.info("dubbo");
        return ApiResultUtils.success(dubboRpcService.getFuserList());
    }

    @RequestMapping("/serverStream")
    public Long serverStream() {
        dubboRpcService.sayHelloServerStream("server stream", new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
                System.out.println(data);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }
        });


        StreamObserver<String> request = dubboRpcService.sayHelloStream(new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
                System.out.println(data);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }
        });
        for (int i = 0; i < 10; i++) {
            request.onNext("stream request" + i);
        }
        request.onCompleted();
        return 1L;
    }

}
