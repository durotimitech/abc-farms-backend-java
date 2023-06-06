package com.abc.farms.abcfarmsbackendjava.services.interfaces;

import com.abc.farms.abcfarmsbackendjava.services.httpServices.errors.BadRequestError;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.errors.ConflictError;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users.LoginRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users.RegisterRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.responseMappings.users.LoginResponse;

public interface UserService {

    public boolean register(RegisterRequest request) throws ConflictError;

    public LoginResponse login(LoginRequest request) throws BadRequestError;
    
}
