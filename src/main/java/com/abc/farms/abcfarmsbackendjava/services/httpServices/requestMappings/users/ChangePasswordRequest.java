package com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {

    @NotBlank(message = "Old Password cannot be null")
    private String oldPassword;

    @NotBlank(message = "New Password cannot be null")
    private String newPassword;

}
