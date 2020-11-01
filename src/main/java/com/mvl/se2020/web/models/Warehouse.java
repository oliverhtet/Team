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

import com.mvl.se2020.web.enumerations.Location;
import com.mvl.se2020.web.enumerations.Status;

@Entity
public class Warehouse {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "warehouse_id")
	private Long id;

	@Column(name = "warehouse_name")
	private String name;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private Status status;

	@Column(name = "location")
	@Enumerated(EnumType.STRING)
	private Location location;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date")
	private Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	private Date modifiedDate;

	@Column(name = "create_user")
	private Long createUserId;

	@Column(name = "modified_user")
	private Long modifiedUserId;

	/*
	 * @Column(name = "product_id") private Long productId;
	 * 
	 * public Long getProductId() { return productId; }
	 * 
	 * public void setProductId(Long productId) { this.productId = productId; }
	 */

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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Long getModifiedUserId() {
		return modifiedUserId;
	}

	public void setModifiedUserId(Long modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Warehouse [id=" + id + ", name=" + name + ", status=" + status + ", location=" + location
				+ ", createDate=" + createDate + ", modifiedDate=" + modifiedDate + ", createUserId=" + createUserId
				+ ", modifiedUserId=" + modifiedUserId + "]";
	}

}
