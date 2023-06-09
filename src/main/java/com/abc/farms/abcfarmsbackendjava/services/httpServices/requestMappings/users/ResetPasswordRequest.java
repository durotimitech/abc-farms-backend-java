package com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {

    @NotBlank(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

}
