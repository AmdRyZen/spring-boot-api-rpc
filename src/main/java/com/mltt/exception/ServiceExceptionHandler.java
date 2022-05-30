package com.mltt.exception;

import com.mltt.utils.ApiResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class ServiceExceptionHandler extends Throwable {

    @Resource
    RocketMQTemplate rocketMQTemplate;

    @Value(value = "${rocketmq.producer.sync-tag}")
    private String syncTag;

    /**
     * 捕获其他异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ServiceException.class, IOException.class, Exception.class})
    public ApiResultUtils handle(ServiceException serviceException) {
        rocketMQTemplate.convertAndSend(syncTag, "code = " + serviceException.getCode() + " message = " +  serviceException.getMessage());
        log.error("ServiceExceptionHandler code：{} message：{}", serviceException.getCode(), serviceException.getMessage());
        return ApiResultUtils.fail(serviceException.getCode(),serviceException.getMessage());
    }
}
