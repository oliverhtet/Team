package com.mvl.se2020.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mvl.se2020.web.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query(value = "Select * from product where product_name like %:name%", nativeQuery = true)
	List<Product> findByName(String name);

}
