package com.abc.farms.abcfarmsbackendjava.services.httpServices;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    private int status;
    private Object data;
    private String message;
    
}
