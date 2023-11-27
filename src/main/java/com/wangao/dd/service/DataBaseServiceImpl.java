package com.wangao.dd.service;

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
    public DataBaseServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
        public List<Map<String, Object>> select(String str) {
        //String sql = "SELECT * FROM your_table_name";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(str);
        return rows;
    }

    @Override
    public int update(String sql) {
        int update = jdbcTemplate.update(sql);
        return update;
    }
}
