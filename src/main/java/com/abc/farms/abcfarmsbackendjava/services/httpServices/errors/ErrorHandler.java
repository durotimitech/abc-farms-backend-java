package com.abc.farms.abcfarmsbackendjava.services.httpServices.errors;

import java.nio.file.AccessDeniedException;
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

import com.abc.farms.abcfarmsbackendjava.services.httpServices.ApiResponse;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = BadRequestError.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse> handleBadRequestError(BadRequestError e) {
        HashMap<String, Object> data = new HashMap<>();

        ApiResponse response = new ApiResponse(HttpStatus.BAD_REQUEST.value(), data, e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = ConflictError.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiResponse> handleConflictError(ConflictError e) {
        HashMap<String, Object> data = new HashMap<>();

        ApiResponse response = new ApiResponse(HttpStatus.CONFLICT.value(), data, e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(value = NotFoundError.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse> handleNotFoundError(NotFoundError e) {
        HashMap<String, Object> data = new HashMap<>();

        ApiResponse response = new ApiResponse(HttpStatus.NOT_FOUND.value(), data, e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = ex.getBindingResult().getFieldErrors()
                .stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        Map<String, Object> data = new HashMap<>();
        data.put("data", errors.toString());
        data.put("statusCode", HttpStatus.BAD_REQUEST.value());
        data.put("message", "Validation failed");

        super.handleMethodArgumentNotValid(ex, headers, status, request);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
    }
}
