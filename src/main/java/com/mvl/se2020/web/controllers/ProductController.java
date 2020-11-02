package com.mvl.se2020.web.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mvl.se2020.web.enumerations.Catagory;
import com.mvl.se2020.web.enumerations.Status;
import com.mvl.se2020.web.models.Product;
import com.mvl.se2020.web.models.Warehouse;
import com.mvl.se2020.web.repository.ProductRepository;
import com.mvl.se2020.web.repository.WarehouseRepositroy;

@Controller
public class ProductController {
	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private WarehouseRepositroy wareRepo;

	@RequestMapping("/product_list")
	public String productList(Model model) {
		System.out.println("Product List Method");

		Product p = new Product();// for filter
		model.addAttribute("product", p);// for filter

		List<Warehouse> wlist = wareRepo.findAllEnable(Status.ENABLE.toString());
		model.addAttribute("wlist", wlist);
		model.addAttribute("categorylist", Catagory.values());

		List<Product> productList = productRepo.findAllProduct(Status.ENABLE.toString());
		model.addAttribute("productList", productList);

		return "product_list";
	}

	@RequestMapping("/create_product")
	public String productCreate(Model model) {

		System.out.println("Product Create Method");

		Product p = new Product();

		List<Warehouse> ware = wareRepo.findAllEnable(Status.ENABLE.toString());
		// System.out.println(ware.get(0).toString());

		model.addAttribute("warehouseList", ware);

		model.addAttribute("categoryList", Catagory.values());
		model.addAttribute("product", p);

		return "create_product";
	}

	@RequestMapping(value = "/create_product", method = RequestMethod.POST)
	public String productCreatePost(Model model, @ModelAttribute Product product) {

		System.out.println("Product Create Method Post");

		product.setCreateDate(new Date());
		product.setModifiedDate(new Date());
		product.setStatus(Status.ENABLE);

		String wname = wareRepo.findById(product.getWareId()).orElseThrow().getName();
		product.setWareName(wname);

		productRepo.save(product);

		List<Product> productList = productRepo.findAllProduct(Status.ENABLE.toString());

		model.addAttribute("productList", productList);

		return "redirect:/product_list";
	}

	@RequestMapping("/edit_product/{id}")
	public String productUpdate(Model model, @PathVariable Long id) {

		Product p = productRepo.findById(id).orElseThrow();
		System.out.println("Product Update Method" + p.toString() + " <<<<<<<<<<<<<<<");
		model.addAttribute("product", p);
		model.addAttribute("categoryList", Catagory.values());

		List<Warehouse> wList = wareRepo.findAllEnable(Status.ENABLE.toString());

		model.addAttribute("warehouseList", wList);

		return "edit_product";
	}

	@RequestMapping(value = "/edit_product", method = RequestMethod.POST)
	public String productUpdatePost(Model model, @ModelAttribute Product p) {

		Product productUpdate = productRepo.findById(p.getId()).orElseThrow();
		System.out.println(productUpdate.toString() + " <<<<<<<<<<<<");
		p.setCreateDate(productUpdate.getCreateDate());
		p.setStatus(productUpdate.getStatus());
		p.setModifiedDate(new Date());

		p.setWareName(wareRepo.findById(p.getWareId()).orElseThrow().getName());

		productRepo.save(p);

		List<Product> pList = productRepo.findAllProduct(Status.ENABLE.toString());
		model.addAttribute("productList", pList);

		return "redirect:/product_list";
	}

	@RequestMapping(value = "/filter_product", method = RequestMethod.POST)
	public String productSearch(Model model, @ModelAttribute Product p) {

		List<Product> plist = null;
		if (p.getName() != null) {
			plist = productRepo.findByName(p.getName().toLowerCase());
		} else {
			plist = productRepo.findAllProduct(Status.ENABLE.toString());
		}

		model.addAttribute("productList", plist);

		List<Warehouse> wlist = wareRepo.findAllEnable(Status.ENABLE.toString());
		model.addAttribute("wlist", wlist);

		model.addAttribute("categorylist", Catagory.values());

		return "product_list";
	}
}
