package com.wangao.dd.service;

import com.wangao.dd.helper.SqlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataBaseServiceImpl implements DataBaseService {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private CacheService cacheService;

    @Autowired
    public DataBaseServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
        public List<Map<String, Object>> select(String str) {
        List<Map<String, Object>> fromCaches = getFromCaches(str);
        if (fromCaches != null){
            return fromCaches;
        }
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(str);
        cacheService.putInCache(rows);
        return rows;
    }

    @Override
    public int update(String sql) {
        int update = jdbcTemplate.update(sql);
        if(update>0){
            cacheService.deleteInCache();
        }
        return update;
    }

    private List<Map<String, Object>> getFromCaches(String str){
        if(str.contains("BETWEEN")){
            int[] booIds = SqlHelper.getBooIds(str);
            List<Map<String, Object>> fromCache = cacheService.getManyFromCache(booIds[0],booIds[1]);
            if (fromCache == null){
                return fromCache;
            }else {
                return null;
            }
        }else {
            int bookId = SqlHelper.getBookId(str);
            List<Map<String, Object>> fromCache = cacheService.getFromCache(bookId);
            if (fromCache == null){
                return fromCache;
            }else {
                return null;
            }
        }
    }
}
