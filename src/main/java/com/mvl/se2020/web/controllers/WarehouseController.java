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

import com.mvl.se2020.web.enumerations.Location;
import com.mvl.se2020.web.enumerations.Status;
import com.mvl.se2020.web.models.Warehouse;
import com.mvl.se2020.web.repository.WarehouseRepositroy;

@Controller
public class WarehouseController {

	@Autowired
	private WarehouseRepositroy wareRepo;

	@RequestMapping("/warehouse_list")
	public String wareIndex(Model model, HttpSession session) {

		/*
		 * if (session.getAttribute("user") == null) {
		 * System.out.println("User Session is null"); } else {
		 * session.setAttribute("user", session); System.out.println("User Session is "
		 * + session.getAttribute("user")); }
		 */
		List<Warehouse> wareList = wareRepo.findAll();

		model.addAttribute("warehouses", wareList);
		System.out.println("Warehouse List Method");

		return null;

	}

	@RequestMapping("/create_ware")
	public String wareCreate(Model model) {

		Warehouse ware = new Warehouse();

		model.addAttribute("locationList", Location.values());
		model.addAttribute("warehouse", ware);
		System.out.println("Warehouse Create Method");

		return "create_ware";

	}

	@RequestMapping(value = "/create_ware", method = RequestMethod.POST)
	public String wareCreatePost(Model model, @ModelAttribute Warehouse warehouse, HttpSession session) {

		warehouse.setStatus(Status.ENABLE);
		warehouse.setCreateDate(new Date());
		warehouse.setModifiedDate(new Date());
		/*
		 * warehouse.setCreateUserId(u.getId()); warehouse.setModifiedUserId(u.getId());
		 */

		wareRepo.save(warehouse);

		List<Warehouse> wareList = wareRepo.findAll();
		model.addAttribute("warehouses", wareList);
		System.out.println("Warehouse Create Post Method");

		return "warehouse_list";

	}

	@RequestMapping("/edit_ware/{id}")
	public String wareEdit(Model model, @PathVariable Long id) {

		Warehouse ware = wareRepo.findById(id).orElseThrow();
		model.addAttribute("warehouse", ware);
		model.addAttribute("locationList", Location.values());
		System.out.println("Warehouse Edit Method");

		return "edit_ware";

	}

	@RequestMapping(value = "/edit_ware", method = RequestMethod.POST)
	public String wareEditPost(Model model, @ModelAttribute Warehouse warehouse, HttpSession session) {
		Warehouse w = warehouse;
		w.setModifiedDate(new Date());

		wareRepo.save(w);

		List<Warehouse> wareList = wareRepo.findAll();

		model.addAttribute("warehouses", wareList);
		System.out.println("Warehouse Create Post Method");

		return "warehouse_list";

	}

}
