package com.abc.farms.abcfarmsbackendjava.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data 
@Builder 
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products_abc_sb")
public class Product {
    @Id
    @GeneratedValue
    private Long id;

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

    private boolean deleted;
}
