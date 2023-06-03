package com.abc.farms.abcfarmsbackendjava.services.httpServices;

import org.springframework.http.HttpStatus;

public class ResponseUtils {

    public static ApiResponse createApiResponse(HttpStatus status, Object data, String message) {
        ApiResponse response = new ApiResponse();
        response.setStatus(status);
        response.setStatusCode(status.value());
        response.setData(data);
        response.setMessage(message);
        

        return response;
    }
}
