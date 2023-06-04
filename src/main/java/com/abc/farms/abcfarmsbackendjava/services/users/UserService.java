package com.abc.farms.abcfarmsbackendjava.services.users;

import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users.LoginRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users.RegisterRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.responseMappings.users.LoginResponse;

public interface UserService {

    public void register(RegisterRequest request);

    public LoginResponse login(LoginRequest request);
    
}
