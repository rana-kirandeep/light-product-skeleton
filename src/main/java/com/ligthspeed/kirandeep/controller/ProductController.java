package com.ligthspeed.kirandeep.controller;


import com.ligthspeed.kirandeep.domain.Product;
import com.ligthspeed.kirandeep.dto.ProductDto;
import com.ligthspeed.kirandeep.response.ApiResponse;
import com.ligthspeed.kirandeep.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public long addProduct(@Valid @RequestBody ProductDto productDto) {
        return productService.saveProduct(Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                        .quantity(productDto.getQuantity())
                        .isActive(productDto.isActive())
                        .baseUnit(productDto.getBaseUnit())
                .build());

    }

    @GetMapping
    public ApiResponse getProducts(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "size", defaultValue = "${page.defaultSize}")
                                   int size) {

        return new ApiResponse(productService.getProducts(page, size));
    }

}
