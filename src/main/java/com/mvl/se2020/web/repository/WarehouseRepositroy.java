package com.mvl.se2020.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mvl.se2020.web.models.Warehouse;

@Repository
public interface WarehouseRepositroy extends JpaRepository<Warehouse, Long> {

	@Query(value = "Select * from warehouse where warehouse_name like %:wname% and status='ENABLE'", nativeQuery = true)
	List<Warehouse> getByName(String wname);

	@Query(value = "Select * from warehouse where status=?1", nativeQuery = true)
	List<Warehouse> getAllEnable(String string);

	/*
	 * @Query(value =
	 * "Select * from warehouse where warehouse.location=location and warehouse_name like %:name% and status='ENABLE'"
	 * , nativeQuery = true) List<Warehouse> getByNameAndLocation(Location location,
	 * String name);
	 */

	@Query(value = "Select * from warehouse where location=?1 and status='ENABLE'", nativeQuery = true)
	List<Warehouse> getByLocation(String string);

}
