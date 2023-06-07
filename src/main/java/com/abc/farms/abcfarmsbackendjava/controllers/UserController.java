package com.abc.farms.abcfarmsbackendjava.controllers;

import java.net.URI;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.abc.farms.abcfarmsbackendjava.services.httpServices.ApiResponse;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.ResponseUtils;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.errors.BadRequestError;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.errors.ConflictError;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users.LoginRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users.RegisterRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users.ResendVerificationRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.responseMappings.users.LoginResponse;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.responseMappings.users.RegisterResponse;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.responseMappings.users.ResendVerificationEmailResponse;
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
            RegisterResponse registerResponse = userService.register(request);
            data.put("verificationCode", registerResponse.getVerificationCode());

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

            ResponseEntity<ApiResponse> response = ResponseUtils.createApiResponse(HttpStatus.OK, loginResponse,
                    "success");
            return response;

        } catch (BadRequestError e) {
            throw e;
        } catch (Exception e) {
            throw new Error(e.getMessage(), e.getCause());
        }

    }

    // TODO: Test
    @GetMapping("/verify-email")
    public ResponseEntity<Void> verifyEmail(@RequestParam("emailVerificationCode") String emailVerificationCode)
            throws BadRequestError {
        logger.info(">>> GET /api/users/verfy-email <<<");

        // HashMap<String, Object> data = new HashMap<>();
        // RedirectView redirectView = new RedirectView();

        try {
            userService.verifyEmail(emailVerificationCode);

            // redirectView.setUrl("http://localhost:3000/auth/login");
            // redirectView.setStatusCode(HttpStatus.OK);
            
            // ResponseEntity<ApiResponse> response = ResponseUtils.createApiResponse(HttpStatus.OK, data, "success");
            return ResponseEntity.status(HttpStatus.FOUND).location(URI
            .create("http://localhost:3000/auth/login")).build();
            
        } catch (BadRequestError e) {
            throw e;
        } catch (Exception e) {
            throw new Error(e.getMessage(), e.getCause());
        }

    }

    // TODO: Test
    @PostMapping("/resend-verification-email")
    public ResponseEntity<ApiResponse> resendVerificationEmail(@RequestBody @Valid ResendVerificationRequest request)
            throws BadRequestError {
        logger.info(">>> POST /api/users/resend-verification-email <<<");

        HashMap<String, Object> data = new HashMap<>();

        try {
            ResendVerificationEmailResponse ResendVerificationEmailResponse = userService.resendVerificationEmail(request);
            data.put("verificationCode", ResendVerificationEmailResponse.getVerificationCode());

            ResponseEntity<ApiResponse> response = ResponseUtils.createApiResponse(HttpStatus.OK, data, "success");
            return response;
        } catch (BadRequestError e) {
            throw e;
        } catch (Exception e) {
            throw new Error(e.getMessage(), e.getCause());
        }

    }
}
