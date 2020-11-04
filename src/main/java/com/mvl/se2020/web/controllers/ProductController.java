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
import com.mvl.se2020.web.service.Service;

@Controller
public class ProductController {
	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private WarehouseRepositroy wareRepo;

	Service service = new Service();

	@RequestMapping("/product_list")
	public String productList(Model model) {
		System.out.println("Product List Method");

		Product p = new Product();// for filter
		model.addAttribute("product", p);// for filter

		List<Warehouse> wlist = wareRepo.getAllEnable(Status.ENABLE.toString());
		model.addAttribute("wlist", wlist);
		model.addAttribute("categorylist", Catagory.values());

		List<Product> productList = productRepo.findAllProduct(Status.ENABLE.toString());
		model.addAttribute("productList", productList);

		return "product_list";
	}

	@RequestMapping("/create_product")
	public String productCreate(Model model) {

		System.out.println("Product Create Method");

		List<Warehouse> ware = wareRepo.getAllEnable(Status.ENABLE.toString());
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
			List<Product> plist = productRepo.findAllProduct(Status.ENABLE.toString());

			isExist = service.isProductExist(plist, product.getName());
			if (!isExist) {
				product.setCreateDate(new Date());
				product.setModifiedDate(new Date());
				product.setStatus(Status.ENABLE);

				String wname = wareRepo.findById(product.getWareId()).orElseThrow().getName();
				product.setWareName(wname);
				productRepo.save(product);

			} else {
				model.addAttribute("errorMsg", isExist);

				model.addAttribute("categoryList", Catagory.values());

				List<Warehouse> ware = wareRepo.getAllEnable(Status.ENABLE.toString());
				model.addAttribute("warehouseList", ware);

				return "create_product";
			}
			return "redirect:/product_list";

		} catch (Exception e) {

			return "redirect:/create_product";
		}

	}

	@RequestMapping("/edit_product/{id}")
	public String productUpdate(Model model, @PathVariable Long id) {

		Product p = productRepo.findById(id).orElseThrow();
		System.out.println("Product Update Method" + p.toString() + " <<<<<<<<<<<<<<<");
		model.addAttribute("product", p);
		model.addAttribute("categoryList", Catagory.values());

		List<Warehouse> wList = wareRepo.getAllEnable(Status.ENABLE.toString());

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
		if (p != null) {

			if (!p.getName().isEmpty() && p.getWareId() != null && p.getCatagory() != null) {
				plist = productRepo.getProductByAllFilter(p.getName().toLowerCase(), p.getWareId(),
						p.getCatagory().toString());
			} else if (!p.getName().isEmpty() && p.getWareId() != null && p.getCatagory() == null) {
				plist = productRepo.getProductByNameAndWareId(p.getName().toLowerCase(), p.getWareId());
			} else if (!p.getName().isEmpty() && p.getWareId() == null && p.getCatagory() != null) {
				plist = productRepo.getProductByNameAndCategory(p.getName().toLowerCase(), p.getCatagory().toString());
			} else if (p.getName().isEmpty() && p.getWareId() != null && p.getCatagory() != null) {
				plist = productRepo.getProductByWareIdandCategory(p.getWareId(), p.getCatagory().toString());
			} else if (p.getName().isEmpty() && p.getWareId() == null && p.getCatagory() != null) {
				plist = productRepo.getProductByCategory(p.getCatagory().toString());
			} else if (p.getName().isEmpty() && p.getWareId() != null && p.getCatagory() == null) {
				plist = productRepo.getProductByWareId(p.getWareId());
			} else if (!p.getName().isEmpty() && p.getWareId() == null && p.getCatagory() == null) {
				plist = productRepo.getProductByName(p.getName().toLowerCase());
			} else {
				plist = productRepo.findAllProduct(Status.ENABLE.toString());
			}

		} else {
			plist = productRepo.findAllProduct(Status.ENABLE.toString());
		}

		model.addAttribute("productList", plist);

		List<Warehouse> wlist = wareRepo.getAllEnable(Status.ENABLE.toString());
		model.addAttribute("wlist", wlist);

		model.addAttribute("categorylist", Catagory.values());

		return "product_list";
	}

	@RequestMapping("/delete_product/{id}")
	public String wareDisable(Model model, @PathVariable Long id) {

		Product product = productRepo.findById(id).orElseThrow();

		product.setModifiedDate(new Date());
		product.setStatus(Status.DISABEL);
		productRepo.save(product);

		return "redirect:/product_list";
	}
}
