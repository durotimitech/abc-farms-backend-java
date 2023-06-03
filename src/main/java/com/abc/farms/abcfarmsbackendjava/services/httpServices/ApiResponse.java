package com.abc.farms.abcfarmsbackendjava.services.httpServices;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    private HttpStatus status;
    private int statusCode;
    private Object data;
    private String message;
    
}
