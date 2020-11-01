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

	public Catagory category;

	@RequestMapping("/product_list")
	public String productList(Model model) {

		System.out.println("Product List Method");
		List<Product> productList = productRepo.findAll();

		model.addAttribute("productList", productList);

		return "product_list";
	}

	@RequestMapping("/create_product")
	public String productCreate(Model model) {

		System.out.println("Product Create Method");

		Product p = new Product();

		List<Warehouse> ware = wareRepo.findAll();
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

		productRepo.save(product);

		List<Product> productList = productRepo.findAll();

		model.addAttribute("productList", productList);

		return "redirect:/product_list";
	}

	@RequestMapping("/edit_product/{id}")
	public String productUpdate(Model model, @PathVariable Long id) {

		System.out.println("Product Update Method");

		model.addAttribute("product", productRepo.findById(id));
		model.addAttribute("categoryList", Catagory.values());

		List<Warehouse> wList = wareRepo.findAll();

		model.addAttribute("warehouseList", wList);

		return "edit_product";
	}

	@RequestMapping(value = "/edit_product", method = RequestMethod.POST)
	public String productUpdatePost(Model model, @ModelAttribute Product p) {

		Product productUpdate = productRepo.findById(p.getId()).orElseThrow();
		productUpdate.setModifiedDate(new Date());
		System.out.println(productUpdate.toString() + " >>>>");
		productRepo.save(productUpdate);
		List<Product> pList = productRepo.findAll();
		model.addAttribute("productList", pList);

		return "product_list";
	}
}
