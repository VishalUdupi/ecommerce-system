package com.vishal.productservice.dto;

import com.vishal.productservice.enums.Category;

import java.math.BigDecimal;

public record ProductRequest(
        String name,
        String description,
        BigDecimal price,
        Category category
){}
