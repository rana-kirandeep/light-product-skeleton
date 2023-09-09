package com.ligthspeed.kirandeep.intigrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ligthspeed.kirandeep.dto.ProductDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.containers.MySQLContainer;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerIntigrationTest {
    public static final String PRODUCTS = "/api/v1/products";
    public static final String MYSQL_DOCKER_IMAGE = "mysql:latest";
    static MySQLContainer mySQLContainer = new MySQLContainer(MYSQL_DOCKER_IMAGE);
    @Autowired
    private MockMvc mvc;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);


    }

    @BeforeAll
    static void beforeAll() {
        mySQLContainer.withReuse(true);
        mySQLContainer.withInitScript("db.sql");
        mySQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        mySQLContainer.stop();
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setup() {
        this.mvc = MockMvcBuilders.standaloneSetup(ProductControllerIntigrationTest.class).build();
    }

    @Test
    void getProductsTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get(PRODUCTS))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".productList[*].name").exists())
                .andExpect(jsonPath(".productList[0].name").value("item1"))
                .andExpect(jsonPath(".productList[0].price").value(10.0))
                .andExpect(jsonPath("@.productList", hasSize(5)));
    }

    @Test
    void getProductsTestWithRequestParameters() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get(PRODUCTS+"?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".productList[*].name").exists())
                .andExpect(jsonPath(".productList[0].name").value("item1"))
                .andExpect(jsonPath(".productList[0].price").value(10.0))
                .andExpect(jsonPath("@.productList", hasSize(10)));
    }

    @Test
    void addProductTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post(PRODUCTS)
                        .content(asJsonString(ProductDto.builder()
                                .name("Iphone14")
                                .isActive(Boolean.TRUE)
                                .price(1200)
                                .quantity(10)
                                .baseUnit("unit")
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
