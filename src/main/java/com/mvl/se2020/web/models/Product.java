package com.mvl.se2020.web.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.mvl.se2020.web.enumerations.Catagory;

@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="product_id")
	private Long id;
	
	@Column(name="product_name")
	private String name;
	
	@Column(name="product_price")
	private Double price;
	
	@Column(name="Qty")
	private Integer quantity;
	
	@Column(name="warehouse_id")
	private Long wareId;
	
	@Column(name="product_catagory")
	@Enumerated(EnumType.STRING)
	private Catagory catagory;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	private Date modifiedDate;
	
	

}
