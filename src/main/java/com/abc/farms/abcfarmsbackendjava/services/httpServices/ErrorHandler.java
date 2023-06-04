package com.abc.farms.abcfarmsbackendjava.services.httpServices;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
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
       
        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                HttpHeaders headers, HttpStatusCode status, WebRequest request) {

                    Map<String, String> errors = ex.getBindingResult().getFieldErrors()
                    .stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

                    Map<String, String> data = new HashMap<>();
                    data.put("data", errors.toString());
                    data.put("status", "400");
                    data.put("message", "Validation failed");


             super.handleMethodArgumentNotValid(ex, headers, status, request);

             return ResponseEntity.badRequest().body(data);
        }
}

