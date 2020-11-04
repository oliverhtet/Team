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

import com.mvl.se2020.web.dto.ProductDTO;
import com.mvl.se2020.web.enumerations.Catagory;
import com.mvl.se2020.web.enumerations.Status;
import com.mvl.se2020.web.models.Product;
import com.mvl.se2020.web.models.Warehouse;
import com.mvl.se2020.web.service.ProductService;
import com.mvl.se2020.web.service.WarehouseService;

@Controller
public class ProductController {

	@Autowired
	private WarehouseService wservice;

	@Autowired
	private ProductService pservice;

	@RequestMapping("/product_list")
	public String productList(Model model) {
		System.out.println("Product List Method");

		List<Warehouse> wlist = wservice.getAllEnable(Status.ENABLE.toString());
		model.addAttribute("wlist", wlist);
		model.addAttribute("categorylist", Catagory.values());

		List<Product> productList = pservice.findAllProduct(Status.ENABLE.toString());
		model.addAttribute("productList", productList);

		model.addAttribute("productdto", new ProductDTO());

		return "product_list";
	}

	@RequestMapping("/create_product")
	public String productCreate(Model model) {

		System.out.println("Product Create Method");

		List<Warehouse> ware = wservice.getAllEnable(Status.ENABLE.toString());
		model.addAttribute("warehouseList", ware);

		model.addAttribute("categoryList", Catagory.values());
		model.addAttribute("product", new Product());

		return "create_product";
	}

	@RequestMapping(value = "/create_product", method = RequestMethod.POST)
	public String productCreatePost(Model model, @ModelAttribute Product product) {

		System.out.println("Product Create Method Post");
		Boolean isExist = false;
		try {
			List<Product> plist = pservice.findAllProduct(Status.ENABLE.toString());

			isExist = pservice.isProductExist(plist, product.getName());
			if (!isExist) {

				product.setCreateDate(new Date());
				product.setModifiedDate(new Date());
				product.setStatus(Status.ENABLE);

				String wname = wservice.getWareById(product.getWareId()).getName();
				product.setWareName(wname);
				pservice.create(product);

			} else {
				model.addAttribute("errorMsg", isExist);

				model.addAttribute("categoryList", Catagory.values());

				List<Warehouse> ware = wservice.getAllEnable(Status.ENABLE.toString());
				model.addAttribute("warehouseList", ware);

				return "create_product";
			}

			List<Product> showList = pservice.findAllProduct(Status.ENABLE.toString());
			model.addAttribute("productList", showList);
			model.addAttribute("productdto", new ProductDTO());
			model.addAttribute("message", "Success");

			return "product_list";

		} catch (Exception e) {

			return "redirect:/create_product";
		}

	}

	@RequestMapping("/edit_product/{id}")
	public String productUpdate(Model model, @PathVariable Long id) {

		Product p = pservice.getProductById(id);
		System.out.println("Product Update Method" + p.toString() + " <<<<<<<<<<<<<<<");
		model.addAttribute("product", p);
		model.addAttribute("categoryList", Catagory.values());

		List<Warehouse> wList = wservice.getAllEnable(Status.ENABLE.toString());

		model.addAttribute("warehouseList", wList);

		return "edit_product";
	}

	@RequestMapping(value = "/edit_product", method = RequestMethod.POST)
	public String productUpdatePost(Model model, @ModelAttribute Product p) {

		Product productUpdate = pservice.getProductById(p.getId());
		System.out.println(productUpdate.toString() + " <<<<<<<<<<<<");
		p.setCreateDate(productUpdate.getCreateDate());
		p.setStatus(productUpdate.getStatus());
		p.setModifiedDate(new Date());

		p.setWareName(wservice.getWareById(p.getWareId()).getName());

		pservice.create(p);

		List<Product> pList = pservice.findAllProduct(Status.ENABLE.toString());
		model.addAttribute("productList", pList);
		model.addAttribute("productdto", new ProductDTO());
		model.addAttribute("update", "Success");
		return "product_list";
	}

	@RequestMapping(value = "/filter_product", method = RequestMethod.POST)
	public String productSearch(Model model, @ModelAttribute ProductDTO dto) {

		List<Product> plist = null;
		if (dto != null) {
			plist = pservice.inquireProduct(dto);

		} else {
			plist = pservice.findAllProduct(Status.ENABLE.toString());
		}

		model.addAttribute("productList", plist);
		model.addAttribute("productdto", dto);

		List<Warehouse> wlist = wservice.getAllEnable(Status.ENABLE.toString());
		model.addAttribute("wlist", wlist);

		model.addAttribute("categorylist", Catagory.values());

		return "product_list";
	}

	@RequestMapping("/delete_product/{id}")
	public String wareDisable(Model model, @PathVariable Long id) {

		Product product = pservice.getProductById(id);

		product.setModifiedDate(new Date());
		product.setStatus(Status.DISABEL);
		pservice.create(product);

		return "redirect:/product_list";
	}
}
