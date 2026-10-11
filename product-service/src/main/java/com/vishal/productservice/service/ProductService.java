package com.vishal.productservice.service;

import com.vishal.productservice.dto.ProductRequest;
import com.vishal.productservice.dto.ProductResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface ProductService {
    void addProduct(ProductRequest request);
    ProductResponse getProductById(Long id);
    List<ProductResponse> getAllProducts();
    void deleteProduct(Long id);
}
