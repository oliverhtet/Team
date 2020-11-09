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

import com.mvl.se2020.web.dto.WarehouseDTO;
import com.mvl.se2020.web.enumerations.Catagory;
import com.mvl.se2020.web.enumerations.Location;
import com.mvl.se2020.web.enumerations.Status;
import com.mvl.se2020.web.models.Product;
import com.mvl.se2020.web.models.Warehouse;
import com.mvl.se2020.web.service.ProductService;
import com.mvl.se2020.web.service.WarehouseService;

@Controller
public class WarehouseController {

	@Autowired
	private WarehouseService wService;
	@Autowired
	private ProductService pService;

	/* Begin View Product in warehouse by WareID */
	@RequestMapping("/admin/warehouse_view/{id}")
	public String warehouseView(Model model, HttpSession session, @PathVariable Long id) {
		Warehouse w = wService.findById(id);
		model.addAttribute("warehouse", w);
		List<Product> plist = pService.getProductByWareId(id);

		if (plist != null) {
			model.addAttribute("productList", plist);
		} else {
			model.addAttribute("productList", null);
		}
		model.addAttribute("catagory", Catagory.values());
		return "admin/warehouse_view";

	} /* end method */

	@RequestMapping("/admin/warehouse_list")
	public String wareIndex(Model model, HttpSession session) {
		List<Warehouse> wareList = wService.getAllEnable(Status.ENABLE.toString());
		model.addAttribute("warehouses", wareList);
		model.addAttribute("searchlocation", Location.values());
		model.addAttribute("warehouseDTO", new WarehouseDTO());
		return "admin/warehouse_list";

	}

	@RequestMapping(value = "/admin/search_warehouse", method = RequestMethod.POST)
	public String warehuseInquery(Model model, HttpSession session, @ModelAttribute WarehouseDTO w) {

		List<Warehouse> wlist = null;
		if (w != null) {
			if (!w.getWareName().isEmpty() && w.getLocation() == null) {
				wlist = wService.getByName(w.getWareName());
			} else if (w.getLocation() != null && w.getWareName().isEmpty()) {
				wlist = wService.getByLocation(w.getLocation().toString());
			} else if (!w.getWareName().isEmpty() && w.getLocation() != null) {
				wlist = wService.getByNameAndLocation(w.getLocation().toString(), w.getWareName());
			} else {
				wlist = wService.getAllEnable(Status.ENABLE.toString());
			}

		} else {
			wlist = wService.getAllEnable(Status.ENABLE.toString());
		}

		model.addAttribute("warehouseDTO", w);
		model.addAttribute("searchlocation", Location.values());
		model.addAttribute("warehouses", wlist);
		return "admin/warehouse_list";

	}

	@RequestMapping("/admin/create_ware")
	public String wareCreate(Model model) {
		Warehouse ware = new Warehouse();
		model.addAttribute("locationList", Location.values());
		model.addAttribute("warehouse", ware);
		return "admin/create_ware";

	}

	@RequestMapping(value = "/admin/create_ware", method = RequestMethod.POST)
	public String wareCreatePost(Model model, @ModelAttribute Warehouse warehouse, HttpSession session) {
		warehouse.setStatus(Status.ENABLE);
		warehouse.setCreateDate(new Date());
		warehouse.setModifiedDate(new Date());
		wService.saveWare(warehouse);
		return "redirect:/admin/warehouse_list";
	}

	@RequestMapping("/admin/edit_ware/{id}")
	public String wareEdit(Model model, @PathVariable Long id) {
		Warehouse ware = wService.findById(id);
		model.addAttribute("warehouse", ware);
		model.addAttribute("locationList", Location.values());
		return "admin/edit_ware";

	}

	@RequestMapping(value = "/admin/edit_ware", method = RequestMethod.POST)
	public String wareEditPost(Model model, @ModelAttribute Warehouse warehouse, HttpSession session) {
		Warehouse db_ware = wService.findById(warehouse.getId());
		warehouse.setCreateDate(db_ware.getCreateDate());
		warehouse.setStatus(db_ware.getStatus());
		warehouse.setModifiedDate(new Date());
		wService.saveWare(warehouse);

		return "redirect:/admin/warehouse_list";
	}

	@RequestMapping("/admin/delete_ware/{id}")
	public String wareDisable(Model model, @PathVariable Long id) {
		Warehouse ware = wService.findById(id);

		List<Product> plist = pService.getProductByWareId(id);
		if (plist.size() != 0) {
			System.out.println(plist.size());
			return "redirect:/admin/warehouse_list";
		} else {

			ware.setModifiedDate(new Date());
			ware.setStatus(Status.DISABEL);
			wService.saveWare(ware);

			return "redirect:/admin/warehouse_list";
		}

	}

}
