package com.mvl.se2020.web.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Warehouse {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="warehouse_id")
	private Long id;
	
	@Column(name="warehouse_name")
	private String name;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	private Date modifiedDate;
}
