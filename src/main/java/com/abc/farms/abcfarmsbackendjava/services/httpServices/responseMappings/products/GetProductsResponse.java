package com.abc.farms.abcfarmsbackendjava.services.httpServices.responseMappings.products;


import java.util.List;

import com.abc.farms.abcfarmsbackendjava.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor 
@NoArgsConstructor
public class GetProductsResponse {

    private List<Product> products;
    private int totalCount;
    
}
