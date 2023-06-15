package com.abc.farms.abcfarmsbackendjava.services.interfaces;

import com.abc.farms.abcfarmsbackendjava.services.httpServices.errors.BadRequestError;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.products.AddProductRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.responseMappings.products.GetProductsResponse;

import jakarta.validation.Valid;

public interface ProductService {

    public void addProduct(@Valid AddProductRequest request);

    public GetProductsResponse getProducts(int pageNumber, int pageSize);

    public void editProduct(Long id, @Valid AddProductRequest request) throws BadRequestError;

    public void deleteProduct(Long productId) throws BadRequestError;
    
}
