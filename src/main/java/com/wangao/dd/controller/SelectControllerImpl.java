package com.wangao.dd.controller;

import com.wangao.dd.service.DataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class SelectControllerImpl implements SelelctController{
    @Autowired
    private DataBaseService dataBaseService;

    @PostMapping("/")
    public  ResponseEntity<?>  getSelectResult(@RequestBody Map<String, String> requestBody) {
        String sql = requestBody.get("str");
        List<Map<String, Object>> select = dataBaseService.select(sql);
        return new ResponseEntity<>(select.toString(),HttpStatus.OK);
    }

}
