package com.abc.farms.abcfarmsbackendjava.controllers;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abc.farms.abcfarmsbackendjava.services.httpServices.ApiResponse;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.ResponseUtils;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.errors.BadRequestError;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.products.AddProductRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.responseMappings.products.GetProductsResponse;
import com.abc.farms.abcfarmsbackendjava.services.interfaces.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private final ProductService productService;

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    // TODO: Test
    @PostMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody @Valid AddProductRequest request) {
        logger.info(">>> POST /api/products <<<");

        HashMap<String, Object> data = new HashMap<>();

        try {
            productService.addProduct(request);

            ResponseEntity<ApiResponse> response = ResponseUtils.createApiResponse(HttpStatus.CREATED, data, "success");

            return response;
        } catch (Exception e) {
            throw new Error(e.getMessage(), e.getCause());
        }

    }

    // TODO: Test
    @GetMapping("")
    public ResponseEntity<ApiResponse> getProducts(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        logger.info(">>> GET /api/products <<<");

        try {
            GetProductsResponse getProductsResponse = productService.getProducts(pageNumber, pageSize);

            ResponseEntity<ApiResponse> response = ResponseUtils.createApiResponse(HttpStatus.OK, getProductsResponse,
                    "success");
            return response;

        } catch (Exception e) {
            throw new Error(e.getMessage(), e.getCause());
        }
    }

    // TODO: Test
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> editProduct(@PathVariable("id") String id, @RequestBody @Valid AddProductRequest request) throws BadRequestError {
        logger.info(">>> PUT /api/products/{id} <<<");

        HashMap<String, Object> data = new HashMap<>();

        try {
            Long productId = Long.parseLong(id);
            productService.editProduct(productId, request);

            ResponseEntity<ApiResponse> response = ResponseUtils.createApiResponse(HttpStatus.OK, data, "success");

            return response;
        } catch (BadRequestError e) {
            throw e;
        }catch (Exception e) {
            throw new Error(e.getMessage(), e.getCause());
        }

    }

    // TODO: Test
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("id") String id) throws BadRequestError {
        logger.info(">>> DELETE /api/products/{id} <<<");

        HashMap<String, Object> data = new HashMap<>();

        try {
            Long productId = Long.parseLong(id);
            productService.deleteProduct(productId);

            ResponseEntity<ApiResponse> response = ResponseUtils.createApiResponse(HttpStatus.OK, data, "success");

            return response;
        } catch (BadRequestError e) {
            throw e;
        }catch (Exception e) {
            throw new Error(e.getMessage(), e.getCause());
        }

    }

}
