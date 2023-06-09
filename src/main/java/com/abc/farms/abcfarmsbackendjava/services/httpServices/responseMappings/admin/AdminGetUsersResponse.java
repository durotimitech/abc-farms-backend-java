package com.abc.farms.abcfarmsbackendjava.services.httpServices.responseMappings.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor 
@NoArgsConstructor
public class AdminGetUsersResponse {

    private int totalCount;
    
}
