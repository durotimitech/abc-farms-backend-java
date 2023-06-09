package com.abc.farms.abcfarmsbackendjava.services.httpServices.responseMappings.users;

import com.abc.farms.abcfarmsbackendjava.interfaces.user.Roles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor 
@NoArgsConstructor
public class LoginResponse {

    private String token;
    private String firstName;
    private boolean isEmailVerified;
    private Roles role;

}
