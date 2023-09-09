package com.ligthspeed.kirandeep.service;


import com.ligthspeed.kirandeep.domain.Product;
import com.ligthspeed.kirandeep.helper.TestHelper;
import com.ligthspeed.kirandeep.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ProductRepository.class,ProductService.class})
public class ProductServiceTest {

    @Autowired
    ProductService productService;

    @MockBean
    ProductRepository productRepository;


    @Test
    void whenProductsAvailableThenReturnProductsTest() {
        when(productRepository.findAll(any(PageRequest.class))).thenReturn(TestHelper.getMockedProductPage());
        List<Product> products=productService.getProducts(0,2);
        assertThat(products, hasSize(2));
    }
    @Test
    void whenProductsNotAvailableThenReturnProductsTest() {
        when(productRepository.findAll(any(PageRequest.class))).thenReturn(TestHelper.getMockedEmptyProductPage());
        List<Product> products=productService.getProducts(0,2);
        assertThat(products, hasSize(0));
    }

    @Test
    void whenProductFieldsPresentThenSaveProductsTest() {
        when(productRepository.save(any(Product.class))).thenReturn(Product.builder().name("test")
                .isActive(Boolean.TRUE).baseUnit("unit"). quantity(20).price(20).id(1l).build());
        Product product =Product.builder().baseUnit("unit") .name("test")
                .isActive(Boolean.TRUE).quantity(20).price(20).build();
        long id=productService.saveProduct(product);
        Mockito.verify(productRepository).save(product);

        assertThat(id, is(1L));
    }



}