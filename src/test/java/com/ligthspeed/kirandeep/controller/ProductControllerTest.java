package com.ligthspeed.kirandeep.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ligthspeed.kirandeep.configuration.AppConfiguration;
import com.ligthspeed.kirandeep.dto.ProductDto;
import com.ligthspeed.kirandeep.helper.TestHelper;
import com.ligthspeed.kirandeep.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = {ProductController.class, AppConfiguration.class}, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class ProductControllerTest {


    public static final String PRODUCTS = "/api/v1/products";
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Environment env;

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void whenAllProductFieldsPresentThenSave() throws Exception {
        when(productService.saveProduct(any())).thenReturn(1L);
        mvc.perform(MockMvcRequestBuilders
                        .post(PRODUCTS)
                        .content(asJsonString(ProductDto.builder()
                                .name("Iphone")
                                .isActive(Boolean.TRUE)
                                .price(1200)
                                .quantity(10)
                                .baseUnit("unit")
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void whenQuantityIsMissingThenError() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post(PRODUCTS)
                        .content(asJsonString(ProductDto.builder()
                                .name("Iphone")
                                .isActive(Boolean.TRUE)
                                .price(1200)
                                .baseUnit("unit")
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("traceId").exists())
                .andExpect(jsonPath("code").value("1002"))
                .andExpect(jsonPath("details").value(messageSource.getMessage("validation.error.quantity.nonzero", null, Locale.ENGLISH)));
    }

    @Test
    void whenQuantityIsZeroMissingThenError() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post(PRODUCTS)
                        .content(asJsonString(ProductDto.builder()
                                .name("Iphone")
                                .isActive(Boolean.TRUE)
                                .quantity(0)
                                .price(1200)
                                .baseUnit("unit")
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("traceId").exists())
                .andExpect(jsonPath("code").value("1002"))
                .andExpect(jsonPath("details").value(messageSource.getMessage("validation.error.quantity.nonzero", null, Locale.ENGLISH)));
    }

    @Test
    void whenQuantityIsNegativeMissingThenError() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post(PRODUCTS)
                        .content(asJsonString(ProductDto.builder()
                                .name("Iphone")
                                .isActive(Boolean.TRUE)
                                .quantity(-3)
                                .price(1200)
                                .baseUnit("unit")
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("traceId").exists())
                .andExpect(jsonPath("code").value("1002"))
                .andExpect(jsonPath("details").value(messageSource.getMessage("validation.error.quantity.nonzero", null, Locale.ENGLISH)));
    }

    @Test
    void whenPriceIsMissingThenError() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post(PRODUCTS)
                        .content(asJsonString(ProductDto.builder()
                                .name("Iphone")
                                .isActive(Boolean.TRUE)
                                .quantity(10)
                                .baseUnit("unit")
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("traceId").exists())
                .andExpect(jsonPath("code").value("1002"))
                .andExpect(jsonPath("details").value(messageSource.getMessage("validation.error.price.nonzero", null, Locale.ENGLISH)));
    }

    @Test
    void whenPriceIsLessThenZeroMissingThenError() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post(PRODUCTS)
                        .content(asJsonString(ProductDto.builder()
                                .name("Iphone")
                                .isActive(Boolean.TRUE)
                                .price(0)
                                .quantity(10)
                                .baseUnit("unit")
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("traceId").exists())
                .andExpect(jsonPath("code").value("1002"))
                .andExpect(jsonPath("details").value(messageSource.getMessage("validation.error.price.nonzero", null, Locale.ENGLISH)));
    }

    @Test
    void whenBaseIsMissingThenError() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post(PRODUCTS)
                        .content(asJsonString(ProductDto.builder()
                                .name("Iphone")
                                .isActive(Boolean.TRUE)
                                .quantity(10)
                                .price(1200)
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("traceId").exists())
                .andExpect(jsonPath("code").value("1002"))
                .andExpect(jsonPath("details").value(messageSource.getMessage("validation.error.baseUnit.notempty", null, Locale.ENGLISH)));
    }

    //TODO Need to write more tested cased for save Product to verify all possible combination of inputs

    @Test
    void whenProductsExistThenReturnProducts() throws Exception {
        when(productService.getProducts(anyInt(), anyInt())).thenReturn(TestHelper.getMockedProductData());
        mvc.perform(MockMvcRequestBuilders
                        .get(PRODUCTS))
                .andExpect(status().isOk()).
                andExpect(jsonPath(".productList[*].name").exists());
    }

    @Test
    void whenProductsAndPageAndSizeParamMissingThenUseDefault() throws Exception {
        when(productService.getProducts(anyInt(), anyInt())).thenReturn(TestHelper.getMockedProductData());
        ArgumentCaptor<Integer> page = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> size = ArgumentCaptor.forClass(Integer.class);

        mvc.perform(MockMvcRequestBuilders
                        .get(PRODUCTS))
                .andExpect(status().isOk()).
                andExpect(jsonPath(".productList[*].name").exists());

        Mockito.verify(productService).getProducts(page.capture(), size.capture());
        assertThat(page.getValue(), is(0));
        assertThat(size.getValue(), is(Integer.valueOf(env.getProperty("page.defaultSize"))));
    }


    @Test
    void whenProductsDontExistThenReturnEmptyProducts() throws Exception {
        when(productService.getProducts(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        mvc.perform(MockMvcRequestBuilders
                        .get(PRODUCTS))
                .andExpect(status().isOk()).
                andExpect(jsonPath("@.productList").isEmpty());
    }


    @Test
    void whenGetProductsThrowExceptionThenReturnError() throws Exception {
        when(productService.getProducts(anyInt(), anyInt())).thenThrow(new RuntimeException("exception"));
        mvc.perform(MockMvcRequestBuilders
                        .get(PRODUCTS))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("traceId").exists())
                .andExpect(jsonPath("code").value("1000"))
                .andExpect(jsonPath("details").value(messageSource.getMessage("1000", null, Locale.ENGLISH)));

    }
}
