package com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.products;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddProductRequest {

    @NotBlank(message = "Product name is mandatory")
    private String name;

    @NotBlank(message = "Product description is mandatory")
    private String description;

    @NotNull(message = "Product price is mandatory")
    private double price;
    
    @NotNull(message = "Product quantity is mandatory")
    private int quantity;

    @NotBlank(message = "Product image url is mandatory")
    private String imageUrl;
    
}
