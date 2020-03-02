package com.sample.orm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT")
public class Product implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9137995640803566434L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "P_ID", nullable = false, unique = true)
	private Integer productId;

	@Column(name = "P_NAME", nullable = false)
	private String productName;

	@Column(name = "P_PRICE", nullable = false)
	private int productPrice;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Product Id; ");
		sb.append(String.valueOf(productId));
		sb.append("\n");
		
		sb.append("Product Name:");
		sb.append(productName);
		sb.append("\n");
		
		sb.append("Product Price: ");
		sb.append(productPrice);
		sb.append("\n");
		
		return sb.toString();
	}
}