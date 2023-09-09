package com.ligthspeed.kirandeep.repository;


import com.ligthspeed.kirandeep.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
