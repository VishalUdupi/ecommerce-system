package com.vishal.productservice.controller;

import com.vishal.productservice.dto.ProductRequest;
import com.vishal.productservice.dto.ProductResponse;
import com.vishal.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody ProductRequest request){
        service.addProduct(request);
        return new ResponseEntity<>(
                "Product has been added",
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id){
        ProductResponse response = service.getProductById(id);

        return new ResponseEntity<>(
               response,
               HttpStatus.OK
       );
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        List<ProductResponse> response = service.getAllProducts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        service.deleteProduct(id);
        return new ResponseEntity<>(
                "Product has been deleted",
                HttpStatus.OK);
    }

}
