package com.mvl.se2020.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvl.se2020.web.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
