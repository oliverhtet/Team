package com.mvl.se2020.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvl.se2020.web.dto.ProductDTO;
import com.mvl.se2020.web.enumerations.Status;
import com.mvl.se2020.web.models.Product;
import com.mvl.se2020.web.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public Boolean isProductExist(List<Product> plist, String name) {

		Boolean isExist = false;
		String n = name.replaceAll("\\s", "").toLowerCase();

		for (Product p : plist) {
			if (n.equals(p.getName().toLowerCase().replaceAll("\\s", ""))) {
				isExist = true;
				break;
			}
		}
		return isExist;
	}

	public List<Product> findAllProduct(String string) {

		List<Product> plist = productRepository.findAllProduct(string);
		if (plist != null) {
			return plist;
		} else {
			return null;
		}
	}

	public void create(Product product) {

		productRepository.save(product);

	}

	public Product getProductById(Long id) {

		Product p = productRepository.findById(id).orElseThrow();
		return p;
	}

	public List<Product> inquireProduct(ProductDTO dto) {

		List<Product> plist;
		if (dto != null) {
			if (!dto.getName().isEmpty() && dto.getWareId() != null && dto.getCatagory() != null) {
				plist = productRepository.getProductByAllFilter(dto.getName().toLowerCase(), dto.getWareId(),
						dto.getCatagory().toString());
			} else if (!dto.getName().isEmpty() && dto.getWareId() != null && dto.getCatagory() == null) {
				plist = productRepository.getProductByNameAndWareId(dto.getName().toLowerCase(), dto.getWareId());
			} else if (!dto.getName().isEmpty() && dto.getWareId() == null && dto.getCatagory() != null) {
				plist = productRepository.getProductByNameAndCategory(dto.getName().toLowerCase(),
						dto.getCatagory().toString());
			} else if (dto.getName().isEmpty() && dto.getWareId() != null && dto.getCatagory() != null) {
				plist = productRepository.getProductByWareIdandCategory(dto.getWareId(), dto.getCatagory().toString());
			} else if (dto.getName().isEmpty() && dto.getWareId() == null && dto.getCatagory() != null) {
				plist = productRepository.getProductByCategory(dto.getCatagory().toString());
			} else if (dto.getName().isEmpty() && dto.getWareId() != null && dto.getCatagory() == null) {
				plist = productRepository.getProductByWareId(dto.getWareId());
			} else if (!dto.getName().isEmpty() && dto.getWareId() == null && dto.getCatagory() == null) {
				plist = productRepository.getProductByName(dto.getName().toLowerCase());
			} else {
				plist = productRepository.findAllProduct(Status.ENABLE.toString());
			}

			return plist;

		} else {
			return null;
		}
	}

	public List<Product> getProductByWareId(Long id) {
		List<Product> plist = productRepository.getProductByWareId(id);
		return plist;
	}

}
