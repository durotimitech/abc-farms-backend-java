// package com.abc.farms.abcfarmsbackendjava.services.httpServices;

// import java.util.HashMap;

// import org.springframework.http.HttpStatus;
// import org.springframework.web.bind.annotation.ControllerAdvice;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.ResponseStatus;

// import com.abc.farms.abcfarmsbackendjava.services.httpServices.errors.NotFoundError;

// @ControllerAdvice
// @ResponseStatus
// public class ErrorHandler {
    
//         @ExceptionHandler(NotFoundError.class)
//         public ApiResponse handleNotFoundError(NotFoundError e) {
//             HashMap<String, Object> data = new HashMap<>();
//             ApiResponse response = ResponseUtils.createApiResponse(HttpStatus.NOT_FOUND, data, e.getMessage());
//             return response;
//         }

// }

