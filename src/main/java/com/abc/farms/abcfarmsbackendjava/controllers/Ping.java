package com.abc.farms.abcfarmsbackendjava.controllers;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abc.farms.abcfarmsbackendjava.services.httpServices.ApiResponse;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.ResponseUtils;
// import com.abc.farms.abcfarmsbackendjava.services.httpServices.errors.NotFoundError;

@RestController
public class Ping {

    @GetMapping("/api/ping")
    public ApiResponse ping() {
    // public ApiResponse ping() throws NotFoundError{
        // throw new NotFoundError("This is an error");
        
        HashMap<String, Object> data = new HashMap<>();

        ApiResponse response = ResponseUtils.createApiResponse(HttpStatus.OK, data, "success");
        return response;
    }
    
}
