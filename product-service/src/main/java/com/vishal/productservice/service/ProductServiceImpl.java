package com.vishal.productservice.service;

import com.vishal.productservice.dto.ProductRequest;
import com.vishal.productservice.dto.ProductResponse;
import com.vishal.productservice.entity.Product;
import com.vishal.productservice.exception.ProductNotFoundException;
import com.vishal.productservice.exception.ProductsTableEmptyException;
import com.vishal.productservice.repository.ProductRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository repository;
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    @PreAuthorize(value = "hasRole('ADMIN')")
    public void addProduct(ProductRequest request) {
        if(request.name() == null || request.name().isEmpty() || request.description() == null || request.description().isEmpty()) {
            throw new IllegalArgumentException("Product details is required");
        }
        if (repository.existsByName(request.name())) {
            throw new IllegalArgumentException("Product already exists");
        }
        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .category(request.category())
                .build();                                   //this can go under a helper method for easier maintainance something like mapToResponse();
        repository.save(product);
    }

    @Override
    @PreAuthorize(value = "isAuthenticated()")
    public ProductResponse getProductById(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return new ProductResponse(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory()
        );
    }

    @Override
    @PreAuthorize(value = "isAuthenticated()")
    public List<ProductResponse> getAllProducts() {
        List<Product> productList = repository.findAll();
        if (productList.isEmpty()) {
            throw new ProductsTableEmptyException("Table is empty");
        }
        List<ProductResponse> products = new ArrayList<>();
        productList.forEach(product -> products.add(new ProductResponse(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory()
        )));
        return products;
    }

    @Override
    @PreAuthorize(value = "hasRole('ADMIN')")
    public void deleteProduct(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        repository.delete(product);
    }
}
