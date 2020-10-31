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
import com.mvl.se2020.web.enumerations.Status;

/**
 * @author MyoGlobal Developer1
 *
 */
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "product_id")
	private Long id;

	@Column(name = "product_name")
	private String name;

	@Column(name = "product_price")
	private Double price;

	@Column(name = "Qty")
	private Integer quantity;

	@Column(name = "warehouse_id")
	private Long wareId;

	@Column(name = "product_catagory")
	@Enumerated(EnumType.STRING)
	private Catagory catagory;

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

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private Status status;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", quantity=" + quantity + ", wareId="
				+ wareId + ", catagory=" + catagory + ", createDate=" + createDate + ", modifiedDate=" + modifiedDate
				+ ", createUserId=" + createUserId + ", modifiedUserId=" + modifiedUserId + ", status=" + status + "]";
	}

}
