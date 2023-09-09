package com.ligthspeed.kirandeep.service;


import com.ligthspeed.kirandeep.domain.Product;
import com.ligthspeed.kirandeep.exception.ApiException;
import com.ligthspeed.kirandeep.repository.ProductRepository;
import com.ligthspeed.kirandeep.util.ProductUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductService {

//    @Autowired
//    Environment environment;

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProducts(int page, int size) {
        log.info("Product is requested to fetch with page: {} and size:{}", page, size);
        return productRepository.findAll(PageRequest.of(page, size)).toList();
    }


    public long saveProduct(Product product) {
        try {
           Product savedProduct =  productRepository.save(product);
           return savedProduct.getId();
        } catch (Exception ex) {
            var traceId = ProductUtil.getUUID();
            log.error("Trace id:[{}] exception occur while saving the product: {}", traceId, product, ex);
            throw new ApiException("exception occurred while saving the product:" + product.getName(),
                    traceId);
        }
    }


}
