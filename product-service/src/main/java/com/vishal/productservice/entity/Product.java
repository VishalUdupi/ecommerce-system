package com.vishal.productservice.entity;

import com.vishal.productservice.enums.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    @Size(max = 100)
    private String name;

    @Column(length = 1000)
    @Size(max = 1000)
    private String description;

    @NotNull
    @Column(nullable = false, precision = 10, scale = 2)
    @Positive
    private BigDecimal price;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime updatedAt;

}
