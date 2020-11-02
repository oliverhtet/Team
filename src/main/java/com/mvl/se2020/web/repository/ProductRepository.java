package com.mvl.se2020.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mvl.se2020.web.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query(value = "Select * from product where status=?1", nativeQuery = true)
	List<Product> findAllProduct(String string);

	/*
	 * @Query(value = "Select * from product where product_name like %:name%",
	 * nativeQuery = true) List<Product> findByName(String name);
	 * 
	 * @Query(value = "Select * from product where warehouse_id=?1", nativeQuery =
	 * true) List<Product> findByWareId(Long wareId);
	 * 
	 * @Query(value = "Select * from product where product_catagory=?1", nativeQuery
	 * = true) List<Product> findByCategory(String string);
	 * 
	 * @Query(value =
	 * "Select * from product where warehouse_id=?1 and product_catagory=?2",
	 * nativeQuery = true) List<Product> findByWareAndCategory(Long wareId, String
	 * string);
	 * 
	 * @Query(value =
	 * "Select * from product where product_name like %:lowerCase% and warehouse_id=?1 and product_catagory=?2"
	 * , nativeQuery = true) List<Product> findByAll(String lowerCase, Long wareId,
	 * String string);
	 */

}
