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

	@Query(value = "Select * from product where product_name like %:name% and warehouse_id=:wareId and product_catagory=:category and status='ENABLE'", nativeQuery = true)
	List<Product> getProductByAllFilter(String name, Long wareId, String category);

	@Query(value = "Select * from product where product_name like %:name% and warehouse_id=:wareId and status='ENABLE'", nativeQuery = true)
	List<Product> getProductByNameAndWareId(String name, Long wareId);

	@Query(value = "Select * from product where product_name like %:name% and product_catagory=:category and status='ENABLE'", nativeQuery = true)
	List<Product> getProductByNameAndCategory(String name, String category);

	@Query(value = "Select * from product where warehouse_id=:wareId and product_catagory=:category and status='ENABLE'", nativeQuery = true)
	List<Product> getProductByWareIdandCategory(Long wareId, String category);

	@Query(value = "Select * from product where product_catagory=:category and status='ENABLE'", nativeQuery = true)
	List<Product> getProductByCategory(String category);

	@Query(value = "Select * from product where warehouse_id=:wareId and status='ENABLE'", nativeQuery = true)
	List<Product> getProductByWareId(Long wareId);

	@Query(value = "Select * from product where product_name like %:name% and status='ENABLE'", nativeQuery = true)
	List<Product> getProductByName(String name);

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
