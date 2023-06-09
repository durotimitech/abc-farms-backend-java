package com.abc.farms.abcfarmsbackendjava.services.interfaces;

import com.abc.farms.abcfarmsbackendjava.services.httpServices.errors.BadRequestError;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.errors.ConflictError;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users.ChangePasswordRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users.LoginRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users.RegisterRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users.ResendVerificationRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users.ResetPasswordRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.responseMappings.users.LoginResponse;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.responseMappings.users.RegisterResponse;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.responseMappings.users.ResendVerificationEmailResponse;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.responseMappings.users.ResetPasswordResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

public interface UserService {

    public RegisterResponse register(@Valid RegisterRequest request) throws ConflictError;

    public LoginResponse login(@Valid LoginRequest request) throws BadRequestError;

    // TODO: Test
    public boolean verifyEmail(String emailVerificationCode) throws BadRequestError;

    // TODO: Test
    public ResendVerificationEmailResponse resendVerificationEmail(@Valid ResendVerificationRequest request) throws BadRequestError;
    
    // TODO: Test
    public ResetPasswordResponse resetPassword(@Valid ResetPasswordRequest request) throws BadRequestError;
    
    // TODO: Test
    public void changePassword(@Valid ChangePasswordRequest request, HttpServletRequest httpRequest) throws BadRequestError;
    
}
