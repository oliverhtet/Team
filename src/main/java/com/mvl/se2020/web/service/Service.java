package com.mvl.se2020.web.service;

import java.util.List;

import com.mvl.se2020.web.models.Product;
import com.mvl.se2020.web.models.User;

public class Service {

	public static void setSessionContext(User u) {

	}

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

}
