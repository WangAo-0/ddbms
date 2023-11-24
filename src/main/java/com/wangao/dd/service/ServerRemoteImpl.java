package com.wangao.dd.service;

import com.wangao.dd.protohelper.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;

@GrpcService
public class ServerRemoteImpl extends RemoteServiceGrpc.RemoteServiceImplBase {
    @Autowired
    private DataBaseService dataBaseService;

    @Override
    public void get(RemoteRequest request, StreamObserver<RemoteResponse> responseObserver) {
        List<Map<String, Object>> elements= dataBaseService.select(request.getSql());
        if (elements.isEmpty()) {
            responseObserver.onNext(null);
            responseObserver.onCompleted();
            return;
        }
        RemoteResponse.Builder builder = RemoteResponse.newBuilder();
        for (int i = 0; i < elements.size(); i++) {
            MyMap.Builder mbuilder = MyMap.newBuilder();
            Map<String,Object> map = elements.get(i);
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            int index = 0;
            for (Map.Entry<String, Object> entry : entries) { // 组装map
                KeyValue keyValue = KeyValue.newBuilder().setKey(entry.getKey()).setValue((String) map.get(entry.getKey()).toString()).build();//map.get(entry.getKey()));
                mbuilder.setEntry(index++,keyValue);
            }
            builder.setData(i,mbuilder.build()); //组装list
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
