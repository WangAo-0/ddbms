package com.wangao.dd.service;

import com.wangao.dd.helper.Caches;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CacheService {
    @Autowired
    public Caches cache;

    public List<Map<String, Object>> getFromCache(Integer userid) {
        //如果所查询的id与本节点数据库匹配，则在此查询并录入缓存。使用No判断。可以使用LIST存储id对应的数据库的号（0，1，2）。
        List<Map<String, Object>> select = new ArrayList<>();
        if (cache.contents.contains(userid)) {
            List<Map<String, Object>> select1 = (List<Map<String, Object>>) cache.contents.get(userid);
            select.addAll(select1);
        } else {
            return null;
        }
        return select;
    }

    public boolean putInCache(List<Map<String, Object>> values) {
        //如果所查询的id与本节点数据库匹配，则在此查询并录入缓存。使用No判断。可以使用LIST存储id对应的数据库的号（0，1，2）。
        for (Map<String, Object> value : values) {
            Integer book_id = (Integer) value.get("book_id");
            cache.contents.put(book_id, value);
        }
        return true;
    }

    public boolean deleteInCache(Integer userid) {
        //如果所查询的id与本节点数据库匹配，则在此查询并录入缓存。使用No判断。可以使用LIST存储id对应的数据库的号（0，1，2）。
        List<Map<String, Object>> select = new ArrayList<>();
        if (cache.contents.contains(userid)) {
            cache.contents.remove(userid);
            return true;
        }
        return false;
    }

    public boolean deleteInCache(){
        cache.contents.clear();
        return true;
    }

    public List<Map<String, Object>> getManyFromCache( int startNum, int endNum) {
        List<Map<String, Object>> select = new ArrayList<>();
        List<Integer> numArray = new ArrayList<>();
        for (int i = startNum; i <= endNum; i++) {
            numArray.add(i);
        }
        for (int num : numArray) {
            if (cache.contents.contains(num)) {
                List<Map<String, Object>> select1 = (List<Map<String, Object>>) cache.contents.get(num);
                select.addAll(select1);
            }
        }
        return select;
    }

}
