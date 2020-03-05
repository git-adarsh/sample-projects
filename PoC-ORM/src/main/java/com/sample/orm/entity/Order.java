package com.sample.orm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ORDERS")
public class Order implements Serializable {

	private static final long serialVersionUID = -7581744138020193191L;

	@Id
	@GeneratedValue(generator = "order-id-generator")
	@GenericGenerator(name = "order-id-generator", strategy = "com.sample.orm.entity.order.generator.OrderIdGenerator")
	@Column(name = "O_ID", nullable = false)
	private Integer orderId;

	@Column(name = "ORDER_DATE")
	private Date orderDate;

	@PrePersist
	protected void onCreate() {
		orderDate = new Date();
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "P_ID")
	private Product product;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId")
	private User user;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Order)) {
			return false;
		}
		Order other = (Order) obj;
		if (orderId == null) {
			if (other.orderId != null) {
				return false;
			}
		} else if (!orderId.equals(other.orderId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Order Id: ");
		sb.append(orderId);
		sb.append("\n");

		sb.append("order date: ");
		sb.append(orderDate);
		sb.append("\n");

		sb.append("product: ");
		sb.append(product != null ? product.toString() : "NULL");
		sb.append("\n");
		
		sb.append("User: ");
		sb.append(user != null ? user.toString() : "NULL");
		sb.append("\n");

		return sb.toString();
	}
}
