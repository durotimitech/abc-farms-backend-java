package com.abc.farms.abcfarmsbackendjava.services.httpServices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse  {

    private int statusCode;
    private Object data;
    private String message;
    
}
