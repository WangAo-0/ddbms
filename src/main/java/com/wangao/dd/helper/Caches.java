package com.wangao.dd.helper;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Caches {
    public ConcurrentHashMap<Integer,Map<String,Object>> contents;//String是主键，Object是List<Map<String, Object>>
    public Map<Integer,String> map;
    public Caches() {
        this.contents = new ConcurrentHashMap<>();
        this.map = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            int no=i+1;
            map.put(i,"grpcserver"+no+"-"+(9517+i));//docker记得命名为grpcserver1,grpcserver2,grpcserver3
//            System.out.printf("grpcserver"+no+"-"+(9527+i));
        }
    }
}