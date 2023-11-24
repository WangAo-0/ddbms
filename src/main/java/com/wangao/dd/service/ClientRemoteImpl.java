package com.wangao.dd.service;
import com.wangao.dd.helper.GrpcUtils;
import com.wangao.dd.protohelper.*;
import org.springframework.stereotype.Service;
import io.grpc.ManagedChannel;

import java.util.List;

@Service
public class ClientRemoteImpl {
    public Object doSelectRemote(String serverAddress, int port, String sql) {
        // String serverAddress = "localhost";
        ManagedChannel channel = GrpcUtils.getChannel(serverAddress, port);
        RemoteServiceGrpc.RemoteServiceBlockingStub stub = RemoteServiceGrpc.newBlockingStub(channel);
        RemoteRequest putRequest = RemoteRequest.newBuilder()
                .setSql(sql)
                .build();
        RemoteResponse get = stub.get(putRequest);
        List<MyMap> dataList = get.getDataList();
        if (!dataList.isEmpty()) {
            return null;
        }
        return dataList;
    }
}

