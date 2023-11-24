package com.wangao.dd.helper;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GrpcUtils {

    @PreDestroy
    public void onShutdown() {
        cacheChannel.values().forEach(value -> value.shutdown());
        cacheChannel.clear();
    }

    private static ConcurrentHashMap<String, ManagedChannel> cacheChannel = new ConcurrentHashMap<String, ManagedChannel>();

    public static ManagedChannel getChannel(String serverAddress, int port) {
        if (cacheChannel.isEmpty()) {
            ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress, port + 10)
                    .usePlaintext()
                    .build();
            cacheChannel.put(serverAddress + (port + 10), channel);
            return channel;
        } else {
            ManagedChannel cachedChannel = cacheChannel.computeIfAbsent(serverAddress + (port + 10), key -> ManagedChannelBuilder.forAddress(serverAddress, port + 10)
                    .usePlaintext()
                    .build());
            if (cachedChannel.isShutdown()) {
                return ManagedChannelBuilder.forAddress(serverAddress, port + 10)
                        .usePlaintext()
                        .build();
            }
            return cachedChannel;
        }
    }
}
