package com.abc.farms.abcfarmsbackendjava.controllers;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abc.farms.abcfarmsbackendjava.services.httpServices.ApiResponse;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.ResponseUtils;

@RestController
public class Ping {

    private final Logger logger = LoggerFactory.getLogger(Ping.class); 

    @GetMapping("/api/ping")
    public ResponseEntity<ApiResponse> ping() {
        logger.info("GET /api/ping");

        HashMap<String, Object> data = new HashMap<>();

        ResponseEntity<ApiResponse> response = ResponseUtils.createApiResponse(HttpStatus.OK, data, "success");
        return response;
    }
    
}
