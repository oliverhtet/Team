package com.mvl.se2020.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mvl.se2020.web.enumerations.Status;
import com.mvl.se2020.web.models.Warehouse;

@Repository
public interface WarehouseRepositroy extends JpaRepository<Warehouse, Long> {

	@Query(value = "Select * from warehouse where warehouse_name=?1", nativeQuery = true)
	Warehouse findByName(String name);

	@Query(value = "Select * from warehouse where status=?1", nativeQuery = true)
	List<Warehouse> getAllWarehouse(Status enable);

}
