package com.abc.farms.abcfarmsbackendjava.services.httpServices.responseMappings.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {

    private String verificationCode;

}
