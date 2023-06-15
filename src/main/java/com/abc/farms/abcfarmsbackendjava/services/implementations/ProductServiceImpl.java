package com.abc.farms.abcfarmsbackendjava.services.implementations;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abc.farms.abcfarmsbackendjava.entities.Product;
import com.abc.farms.abcfarmsbackendjava.repositories.ProductRepository;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.errors.BadRequestError;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.products.AddProductRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.responseMappings.products.GetProductsResponse;
import com.abc.farms.abcfarmsbackendjava.services.interfaces.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductRepository productRepository;

    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public void addProduct(@Valid AddProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .imageUrl(request.getImageUrl())
                .deleted(false)
                .build();

        productRepository.save(product);
    }

    @Override
    public GetProductsResponse getProducts(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<Product> products = productRepository.findAll(pageable).getContent();
        int totalCount = productRepository.findAll().size();

        // int totalElements = (int)
        // productRepository.findAll(pageable).getTotalElements();
        // int totalPages = (int) productRepository.findAll(pageable).getTotalPages();

        GetProductsResponse response = GetProductsResponse
                .builder()
                .products(products)
                .totalCount(totalCount)
                .build();

        return response;
    }

    @Override
    public void editProduct(Long id, @Valid AddProductRequest request) throws BadRequestError {
        Optional<Product> products = productRepository.findById(id);

        if (products.isEmpty()) {
            throw new BadRequestError("Product not found");
        }

        Product product = products.get();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setImageUrl(request.getImageUrl());

        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) throws BadRequestError {
        Optional<Product> products = productRepository.findById(productId);

        if (products.isEmpty()) {
            throw new BadRequestError("Product not found");
        }

        Product product = products.get();

        System.out.println(">>>><<<<<<<<");
        System.out.println(product);
        product.setDeleted(!product.isDeleted());
        System.out.println(product);

        productRepository.save(product);
    }

}
