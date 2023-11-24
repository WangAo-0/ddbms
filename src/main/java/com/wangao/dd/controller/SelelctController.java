package com.wangao.dd.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface SelelctController {
    /**
     * 传入select语句
     *
     * @param requestBody
     * @return
     */
    @PostMapping("/")
    ResponseEntity<?> getSelectResult(@RequestBody Map<String, String> requestBody);
}
