package com.abc.farms.abcfarmsbackendjava.services.httpServices;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.abc.farms.abcfarmsbackendjava.services.httpServices.errors.NotFoundError;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {
    
        @ExceptionHandler(value = NotFoundError.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public ApiResponse handleNotFoundError(NotFoundError e) {
            HashMap<String, Object> data = new HashMap<>();
            
            ApiResponse response = ResponseUtils.createApiResponse(HttpStatus.NOT_FOUND, data, e.getMessage());
            return response;
        }

}

