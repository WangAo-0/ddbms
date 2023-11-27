package com.wangao.dd.service;

import java.util.List;
import java.util.Map;

public interface DataBaseService {
    List<Map<String, Object>> select(String str);

    int update(String sql);
}
