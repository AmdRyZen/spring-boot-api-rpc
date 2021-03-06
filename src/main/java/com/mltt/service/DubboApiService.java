package com.mltt.service;

import com.mltt.biz.dto.DubboDto;
import com.mltt.biz.model.FUser;
import org.apache.dubbo.common.stream.StreamObserver;
import org.springframework.stereotype.Service;

@Service
public interface DubboApiService {
    FUser getFuserList(DubboDto dubboDto);

    void sayHelloServerStream(String server_stream, StreamObserver<String> onCompleted);

    StreamObserver<String> sayHelloStream(StreamObserver<String> onCompleted);
}
