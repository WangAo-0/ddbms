package com.wangao.dd.controller;

import com.wangao.dd.service.DataBaseService;
import com.wangao.dd.service.DepartSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class SelectControllerImpl implements SelelctController{
    @Autowired
    private DepartSQL departSQL;

    @PostMapping("/")
    public  ResponseEntity<?>  getSelectResult(@RequestBody Map<String, String> requestBody) {
        String sql = requestBody.get("str");
        List<Map<String, Object>> selectRows = departSQL.sql(sql);
        if(selectRows == null){
            return new ResponseEntity<>("#not found\n",HttpStatus.OK);
        }
        return new ResponseEntity<>(selectRows.toString()+"\n",HttpStatus.OK);
    }

}
