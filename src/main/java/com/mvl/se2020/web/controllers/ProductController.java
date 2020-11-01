package com.mvl.se2020.web.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mvl.se2020.web.enumerations.Catagory;
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

		for (Product p : productList) {
			System.out.println(">>>" + p.getCatagory());
		}
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

		productRepo.save(product);

		List<Product> productList = productRepo.findAll();

		model.addAttribute("productList", productList);

		return "redirect:/product_list";
	}
}
