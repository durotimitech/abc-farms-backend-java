package com.abc.farms.abcfarmsbackendjava.controllers;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abc.farms.abcfarmsbackendjava.services.httpServices.ApiResponse;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.ResponseUtils;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.errors.BadRequestError;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.errors.ConflictError;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users.LoginRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users.RegisterRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.responseMappings.users.LoginResponse;
import com.abc.farms.abcfarmsbackendjava.services.interfaces.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid RegisterRequest request) throws ConflictError {
        logger.info(">>> POST /api/users/register <<<");

        HashMap<String, Object> data = new HashMap<>();

        try {
            userService.register(request);

            ResponseEntity<ApiResponse> response = ResponseUtils.createApiResponse(HttpStatus.CREATED, data, "success");
            return response;
        } catch (ConflictError e) {
            throw e;
        } catch (Exception e) {
            throw new Error(e.getMessage(), e.getCause());
        }

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid LoginRequest request) throws BadRequestError {
        logger.info(">>> POST /api/users/login <<<");

        try {
            LoginResponse loginResponse = userService.login(request);

            ResponseEntity<ApiResponse> response = ResponseUtils.createApiResponse(HttpStatus.OK, loginResponse, "success");
            return response;

        } catch (BadRequestError e) {
            throw e;}
         catch (Exception e) {
            throw new Error(e.getMessage(), e.getCause());
        }

    }
}
