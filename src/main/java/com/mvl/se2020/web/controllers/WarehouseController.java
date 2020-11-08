package com.mvl.se2020.web.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mvl.se2020.web.enumerations.Catagory;
import com.mvl.se2020.web.enumerations.Location;
import com.mvl.se2020.web.enumerations.Status;
import com.mvl.se2020.web.models.Product;
import com.mvl.se2020.web.models.Warehouse;
import com.mvl.se2020.web.repository.ProductRepository;
import com.mvl.se2020.web.repository.WarehouseRepositroy;

@Controller
public class WarehouseController {

	@Autowired
	private WarehouseRepositroy warehouseRepositroy;
	@Autowired
	private ProductRepository productRepository;

	/* Begin View Product in warehouse by WareID */
	@RequestMapping("/warehouse_view/{id}")
	public String warehouseView(Model model, HttpSession session, @PathVariable Long id) {
		Warehouse w = warehouseRepositroy.findById(id).orElseThrow();
		model.addAttribute("warehouse", w);
		List<Product> plist = productRepository.getProductByWareId(id);

		if (plist != null) {
			model.addAttribute("productList", plist);
		} else {
			model.addAttribute("productList", null);
		}
		model.addAttribute("catagory", Catagory.values());
		return "warehouse_view";

	} /* end method */

	@RequestMapping("/warehouse_list")
	public String wareIndex(Model model, HttpSession session) {
		List<Warehouse> wareList = warehouseRepositroy.getAllEnable(Status.ENABLE.toString());
		model.addAttribute("warehouses", wareList);
		model.addAttribute("searchlocation", Location.values());
		model.addAttribute("warehouse", new Warehouse());
		return "warehouse_list";

	}

	@RequestMapping(value = "/search_warehouse", method = RequestMethod.POST)
	public String warehuseInquery(Model model, HttpSession session, @ModelAttribute Warehouse w) {

		List<Warehouse> wlist = null;
		if (w != null) {
			if (!w.getName().isEmpty() && w.getLocation() == null) {
				wlist = warehouseRepositroy.getByName(w.getName());
			} else if (w.getLocation() != null && w.getName().isEmpty()) {
				wlist = warehouseRepositroy.getByLocation(w.getLocation().toString());
			} else if (!w.getName().isEmpty() && w.getLocation() != null) {
				wlist = warehouseRepositroy.getByNameAndLocation(w.getLocation().toString(), w.getName());
			} else {
				wlist = warehouseRepositroy.getAllEnable(Status.ENABLE.toString());
			}

		} else {
			wlist = warehouseRepositroy.getAllEnable(Status.ENABLE.toString());
		}

		model.addAttribute("warehouse", w);
		model.addAttribute("searchlocation", Location.values());
		model.addAttribute("warehouses", wlist);
		return "warehouse_list";

	}

	@RequestMapping("/create_ware")
	public String wareCreate(Model model) {
		Warehouse ware = new Warehouse();
		model.addAttribute("locationList", Location.values());
		model.addAttribute("warehouse", ware);
		return "create_ware";

	}

	@RequestMapping(value = "/create_ware", method = RequestMethod.POST)
	public String wareCreatePost(Model model, @ModelAttribute Warehouse warehouse, HttpSession session) {
		warehouse.setStatus(Status.ENABLE);
		warehouse.setCreateDate(new Date());
		warehouse.setModifiedDate(new Date());
		warehouseRepositroy.save(warehouse);
		List<Warehouse> wareList = warehouseRepositroy.getAllEnable(Status.ENABLE.toString());
		model.addAttribute("warehouses", wareList);
		model.addAttribute("message1", "Success");
		return "redirect:/warehouse_list";
	}

	@RequestMapping("/edit_ware/{id}")
	public String wareEdit(Model model, @PathVariable Long id) {
		Warehouse ware = warehouseRepositroy.findById(id).orElseThrow();
		model.addAttribute("warehouse", ware);
		model.addAttribute("locationList", Location.values());
		return "edit_ware";

	}

	@RequestMapping(value = "/edit_ware", method = RequestMethod.POST)
	public String wareEditPost(Model model, @ModelAttribute Warehouse warehouse, HttpSession session) {
		Warehouse db_ware = warehouseRepositroy.findById(warehouse.getId()).orElseThrow();
		warehouse.setCreateDate(db_ware.getCreateDate());
		warehouse.setStatus(db_ware.getStatus());
		warehouse.setModifiedDate(new Date());
		warehouseRepositroy.save(warehouse);
		List<Warehouse> wareList = warehouseRepositroy.getAllEnable(Status.ENABLE.toString());
		model.addAttribute("warehouses", wareList);
		model.addAttribute("message", "Success");
		return "warehouse_list";
	}

	@RequestMapping("/delete_ware/{id}")
	public String wareDisable(Model model, @PathVariable Long id) {
		Warehouse ware = warehouseRepositroy.findById(id).orElseThrow();

		List<Product> plist = productRepository.getProductByWareId(id);
		if (plist != null) {

			return "redirect:/warehouse_list";
		} else {

			ware.setModifiedDate(new Date());
			ware.setStatus(Status.DISABEL);
			warehouseRepositroy.save(ware);
			model.addAttribute("delete", "Success");
			return "redirect:/warehouse_list";
		}
	}

}
