package com.mvl.se2020.web.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvl.se2020.web.enumerations.Status;
import com.mvl.se2020.web.models.Warehouse;
import com.mvl.se2020.web.repository.WarehouseRepositroy;

@Service
public class WarehouseService {
	// zmh
	@Autowired
	private WarehouseRepositroy warehouseRepositroy;

	public List<Warehouse> getAllEnable(String string) {

		List<Warehouse> wlist = warehouseRepositroy.getAllEnable(Status.ENABLE.toString());
		if (wlist != null) {

			return wlist;
		} else {
			return null;
		}

	}

	public Warehouse getWareById(Long wareId) {

		Warehouse w = warehouseRepositroy.getWareById(wareId);
		if (w != null) {
			return w;
		} else {
			return null;
		}

	}

	public Warehouse findById(Long id) {
		return warehouseRepositroy.findById(id).orElseThrow();
	}

	public List<Warehouse> getByName(String wareName) {
		return warehouseRepositroy.getByName(wareName);
	}

	public List<Warehouse> getByLocation(String string) {
		return warehouseRepositroy.getByLocation(string);
	}

	public List<Warehouse> getByNameAndLocation(String location, String wareName) {
		return warehouseRepositroy.getByNameAndLocation(location, wareName);
	}

	public void saveWare(Warehouse ware) {
		ware.setModifiedDate(new Date());
		warehouseRepositroy.save(ware);

	}

}
