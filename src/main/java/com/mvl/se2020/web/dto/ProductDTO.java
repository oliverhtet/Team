package com.mvl.se2020.web.dto;

import com.mvl.se2020.web.enumerations.Catagory;

public class ProductDTO {
	//for filter
	private Long id;
	private String name;
	private Long wareId;
	private Catagory catagory;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getWareId() {
		return wareId;
	}

	public void setWareId(Long wareId) {
		this.wareId = wareId;
	}

	public Catagory getCatagory() {
		return catagory;
	}

	public void setCatagory(Catagory catagory) {
		this.catagory = catagory;
	}

	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", name=" + name + ", wareId=" + wareId + ", catagory=" + catagory + "]";
	}

}
