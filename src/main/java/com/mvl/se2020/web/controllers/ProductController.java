package com.mvl.se2020.web.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
		List<Warehouse> ware = wservice.getAllEnable(Status.ENABLE.toString());
		model.addAttribute("warehouseList", ware);
		model.addAttribute("categoryList", Catagory.values());
		model.addAttribute("product", new Product());
		return "create_product";
	}

	@RequestMapping(value = "/create_product", method = RequestMethod.POST)
	public String productCreatePost(Model model, @ModelAttribute Product product,
			@RequestParam("fileImage") MultipartFile multipartFile) {
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

				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				product.setImage(fileName);

				pservice.create(product);

				String uploadDir = "product-photos/" + product.getId();

				FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

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
			model.addAttribute("categorylist", Catagory.values());
			model.addAttribute("wlist", wservice.getAllEnable(Status.ENABLE.toString()));
			model.addAttribute("message", "Success");
			return "product_list";
		} catch (Exception e) {
			return "redirect:/create_product";
		}
	}

	@RequestMapping("/edit_product/{id}")
	public String productUpdate(Model model, @PathVariable Long id) {
		Product p = pservice.getProductById(id);
		model.addAttribute("product", p);
		model.addAttribute("categoryList", Catagory.values());
		List<Warehouse> wList = wservice.getAllEnable(Status.ENABLE.toString());
		model.addAttribute("warehouseList", wList);

		System.out.println(p.getImage());
		return "edit_product";
	}

	@RequestMapping(value = "/edit_product", method = RequestMethod.POST)
	public String productUpdatePost(Model model, @ModelAttribute Product p) {
		Product productUpdate = pservice.getProductById(p.getId());
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
