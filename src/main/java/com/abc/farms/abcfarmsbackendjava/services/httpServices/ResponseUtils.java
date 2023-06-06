package com.abc.farms.abcfarmsbackendjava.services.httpServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {
    private static final Logger logger = LoggerFactory.getLogger(ResponseUtils.class); 

    public static ResponseEntity<ApiResponse> createApiResponse(HttpStatus status, Object data, String message) {
        ApiResponse response = new ApiResponse();
        response.setStatusCode(status.value());
        response.setData(data);
        response.setMessage(message);

        logger.info(">>> {}", response.toString());

        return ResponseEntity.status(status).body(response);
    }
}
