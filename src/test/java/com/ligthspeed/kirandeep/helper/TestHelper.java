package com.ligthspeed.kirandeep.helper;


import com.ligthspeed.kirandeep.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public class TestHelper {

    public static List<Product> getMockedProductData() {

        List<Product> productList = List.of(
                Product.builder()
                        .name("Iphone")
                        .price(1200.00)
                        .baseUnit("unit")
                        .isActive(Boolean.TRUE)
                        .quantity(10)
                        .id(1l)

                        .build(),
                Product.builder()
                        .name("Samsung")
                        .price(1000)
                        .baseUnit("unit")
                        .isActive(Boolean.TRUE)
                        .quantity(10)
                        .id(2l)
                        .build());

        return productList;
    }

    public static Page<Product> getMockedProductPage() {
        return new PageImpl<>(List.of(
                Product.builder()
                        .name("Iphone")
                        .price(1200.00)
                        .baseUnit("unit")
                        .isActive(Boolean.TRUE)
                        .quantity(10)
                        .id(1l)

                        .build(),
                Product.builder()
                        .name("Samsung")
                        .price(1000)
                        .baseUnit("unit")
                        .isActive(Boolean.TRUE)
                        .quantity(10)
                        .id(2l)
                        .build()));
    }

    public static Page<Product> getMockedEmptyProductPage() {
        return new PageImpl<>(List.of(
                ));
    }

}
